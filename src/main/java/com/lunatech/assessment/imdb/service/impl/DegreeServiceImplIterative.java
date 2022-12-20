package com.lunatech.assessment.imdb.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.jayway.jsonpath.Option;
import com.lunatech.assessment.imdb.dto.DegreeDto;
import com.lunatech.assessment.imdb.dto.DegreePathItem;
import com.lunatech.assessment.imdb.dto.NameDTO;
import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;
import com.lunatech.assessment.imdb.model.summary.TitleSummary;
import com.lunatech.assessment.imdb.repository.NamesSummaryRepository;
import com.lunatech.assessment.imdb.repository.TitleSummaryRepository;
import com.lunatech.assessment.imdb.service.DegreeService;
import com.lunatech.assessment.imdb.service.NameService;
import com.lunatech.assessment.imdb.service.TitleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

@Service
@Getter
@Setter
@Qualifier("iterative")
public class DegreeServiceImplIterative implements DegreeService{

    @Delegate private State state; 
    @Getter
    @Setter
    @AllArgsConstructor
    private class State{
        private int currentDegree=0;
        private List<String> visitedTitles= new ArrayList<>(); 
        private List<String> visitedNames = new ArrayList<>(); 
        List<String> path; 
    }
    @Getter
    @Setter
    @AllArgsConstructor
    private class NameWithDegree{
        int degree; 
        String name; 
        List<ActorInFilm> path;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    private class ActorInFilm{
        String name; 
        String movie; 
    }
    @Autowired
    private NameService nameService; 
    @Autowired
    private TitleService titleService;

    @Autowired
    private NamesSummaryRepository namesSummaryRepository; 
    @Autowired
    private TitleSummaryRepository titleSummaryRepository; 

    @Autowired
    private ModelMapper modelMapper; 


    
    /** 
     * @param targetActor
     * @param sourceActor
     * @return int
     * @throws Exception
     */
    @Override
    public DegreeDto getDegreeOfReachbetweenActors(String targetActor, String sourceActor) throws Exception {
        LinkedList<NameWithDegree> queue = new LinkedList<>(); 
        List<ActorInFilm> pathHistory = new ArrayList<>();
        queue.add(new NameWithDegree(1, sourceActor, pathHistory)); 
        State searchState = new State(0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); 
        DegreeDto path = degreeSearch(searchState, queue, targetActor, sourceActor); 
        return path; 
    }


    
    /** 
     * @param state
     * @param queue
     * @param targetName
     * @return Optional<State>
     */
    private DegreeDto degreeSearch(State state, LinkedList<NameWithDegree> queue, String targetName, String sourceName){
        NameWithDegree secondLastElement = null; 

        while(!queue.isEmpty()){
        
        NameWithDegree element = queue.remove(); 
        String nextNameToTraverse = element.getName(); 
        
        //adding nodes in visiting list
        state.getVisitedNames().add(nextNameToTraverse);

        //processing the name 
        List<ActorInFilm> linkedNames = getLinkedTitleIds(nextNameToTraverse)
            .stream()
            //intersecting currently all linked titles list with state visited list
            .filter(Predicate.not(state.getVisitedTitles()::contains))
            .filter(Objects::nonNull)
            .flatMap(title -> {
                state.getVisitedTitles().add(title); 
                return getLinkedNameIds(title).stream(); 
                })
            .toList(); 
            
        // check if all fetched linkedNames from current 'element'contains target actor (which is Kevin Bacon for 'six degree of bacon' number)    
        boolean nameResult = linkedNames.stream().anyMatch(actionNactor -> { 
                    boolean matched = actionNactor.getName().equalsIgnoreCase(targetName);
                    if(matched) { element.getPath().add(actionNactor);}
                    return matched; 
                });
        
        //start searching for target name from one degree linked names to next degree if not found. 
        if(!nameResult){

            //adding current degree plus One with all linked name to be seached later from queue. 
            queue.addAll(linkedNames.stream()
                              //  .filter(Predicate.not(state.getVisitedNames()::contains))
                                .filter(nameNmovie ->  !state.getVisitedNames().contains(nameNmovie.getName()))
                                .filter(Objects::nonNull)
                                .map(linkedName -> {
                                    List<ActorInFilm> pathHistory = new ArrayList<>();
                                    pathHistory.addAll(element.getPath()); 
                                    pathHistory.add(linkedName); 
                                    return new NameWithDegree(element.getDegree() + 1, linkedName.getName(),pathHistory);
                                })
                                .toList());
        }else {
            secondLastElement = element; 
            break; 
        }
    }
        state.setCurrentDegree(secondLastElement.getDegree());
        List<String> titleIds = new ArrayList<>();
        List<String> nameIds = new ArrayList<>();
        nameIds.add(sourceName);
        state.setPath(secondLastElement.getPath()
                        .stream()
                        .map(actionInFilm -> {
                                titleIds.add(actionInFilm.getMovie());
                                nameIds.add(actionInFilm.getName()); 
                                return new StringBuilder()
                                    .append(actionInFilm.getMovie())
                                    .append("-->")
                                    .append(actionInFilm.getName())
                                    .toString();
                        })
                        .toList());

        return getDetailPath(secondLastElement.getDegree(), nameIds,titleIds); 
    }

    private DegreeDto getDetailPath(int degree, List<String> names, List<String> titles){
        DegreeDto dto = new DegreeDto(); 
        dto.setBaconDegree(degree);

        //storing name --> move each step in map entry and map represent the full path
        Map<String,String> steps = new LinkedHashMap<>(); 
        int nameSize = names.size(); 
        for(int i= 0; i< nameSize; i++ ){
            if(i < nameSize - 1 ){
                steps.put(names.get(i), titles.get(i)); 
            }else {
                steps.put(names.get(i), null); 
            }
        }

        List<NameSmmary> namesSummary = namesSummaryRepository.getAllNamesFromIds(names); 
        List<TitleSummary> titlesSummary = titleSummaryRepository.getAllTitlesFromIds(titles);
        
        DegreePathItem pathItem; 
        List<DegreePathItem> paths = new ArrayList<>(); 

        //fetching details path from from ids 
        for(Map.Entry<String,String> entry : steps.entrySet()){
            pathItem = new DegreePathItem(); 

            NameSmmary nm = namesSummary.stream().filter(nameSummmary -> nameSummmary.getNconst().equalsIgnoreCase(entry.getKey())).findFirst().get();
            pathItem.setName(modelMapper.map(nm, NameDTO.class));
            
            if(null != entry.getValue()){
                TitleSummary tm = titlesSummary.stream().filter(titleSummmary -> titleSummmary.getTconst().equalsIgnoreCase(entry.getValue())).findFirst().get();
                pathItem.setTitle(modelMapper.map(tm, TitleDTO.class));
            }
            paths.add(pathItem); 
        }
        dto.setPath(paths);
        return dto; 
    }

    /** 
     * @param title
     * @return Set<ActorInFilm>
     */
    private Set<ActorInFilm> getLinkedNameIds(String title){
        return titleService.getTitleWithPrincipalsById(title).orElseThrow().getPrincipalsList().stream().map(principal ->  new ActorInFilm(principal.getId().getNconstId(), title)).collect(Collectors.toSet());
    }
    
    /** 
     * @param name
     * @return Set<String>
     */
    private Set<String> getLinkedTitleIds(String name){
        return nameService.getNameById(name).orElseThrow().getPrincipalsList().stream().map(principal -> principal.getId().getTconstId()).collect(Collectors.toSet());
    }
    
}