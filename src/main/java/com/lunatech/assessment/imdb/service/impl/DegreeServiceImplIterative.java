package com.lunatech.assessment.imdb.service.impl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lunatech.assessment.imdb.constants.ImdbI18NConstants;
import com.lunatech.assessment.imdb.dto.DegreeDto;
import com.lunatech.assessment.imdb.dto.DegreePathItem;
import com.lunatech.assessment.imdb.dto.NameDTO;
import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.exceptions.ImdbNotFoundException;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;
import com.lunatech.assessment.imdb.model.summary.TitleSummary;
import com.lunatech.assessment.imdb.repository.NamesSummaryRepository;
import com.lunatech.assessment.imdb.repository.TitleSummaryRepository;
import com.lunatech.assessment.imdb.service.DegreeService;
import com.lunatech.assessment.imdb.service.NameService;
import com.lunatech.assessment.imdb.service.TitleService;
import com.lunatech.assessment.imdb.util.ImdbUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @NoArgsConstructor
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
    @Autowired
    private ImdbUtils utils; 


    
    /** 
     * Get degree between sourceActor and tagetActor. It also provide the path between source and target. 
     *  
     * @param targetActor
     * @param sourceActor
     * @return int
     * @throws Exception
     */
    @Cacheable("degreePath")
    @SuppressWarnings("java:S2201")
    @Override
    public DegreeDto getDegreeOfReachbetweenActors(String targetActor, String sourceActor){
        LinkedList<NameWithDegree> queue = new LinkedList<>(); 
        List<ActorInFilm> pathHistory = new ArrayList<>();
        //Validating whether targetActor exist. SourceActor will be verified when processing start with sourceActor
        nameService.getNameById(targetActor)
                    .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbI18NConstants.NAME_NOT_FOUND, targetActor)));
        queue.add(new NameWithDegree(1, sourceActor, pathHistory)); 
        State searchState = new State(0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); 
        return degreeSearch(searchState, queue, targetActor, sourceActor); 
    }


    
    /** 
     * This function uses below algorithm to fetch "degree of reach" between source and target actor. 
     * 
     * 1. start from source actor 
     * 2. fetchs all linked titles (all titles this actor was part of)
     * 3. For each title (removing already visited titles from state), fetch all linked names (actors) (removing already visited name from state) and collect all linked names for all titles. 
     * 4. This is called One Degree (One Step). 
     * 5. Check whether target actor is in above fetched linked names list. 
     * 6. If it exist then current degree is returned with detailed path
     * 7. Otherwise, Add one degree to current degree and repeat from step 2 until target actor is found or all title and names are consumed. 
     * 
     * @param state
     * @param queue
     * @param targetName
     * @return Optional<State>
     */
    private DegreeDto degreeSearch(State state, LinkedList<NameWithDegree> queue, String targetName, String sourceName){
        NameWithDegree secondLastElement = new NameWithDegree(); 

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
        return getpath(state, sourceName, secondLastElement); 
    }



    private DegreeDto getpath(State state, String sourceName, NameWithDegree secondLastElement) {
        state.setCurrentDegree(secondLastElement.getDegree());
        List<String> titleIds = new ArrayList<>();
        List<String> nameIds = new ArrayList<>();
        nameIds.add(sourceName);
        secondLastElement.getPath().stream()
                        .map(actionInFilm -> {
                                titleIds.add(actionInFilm.getMovie());
                                nameIds.add(actionInFilm.getName()); 
                                return null; 
                        }).toList();

        return getDetailedPath(secondLastElement.getDegree(), nameIds,titleIds);
    }

    
    /** 
     * Get detailed path from nameIds list and titleIds list. 
     * 
     * @param degree
     * @param names
     * @param titles
     * @return DegreeDto
     */
    private DegreeDto getDetailedPath(int degree, List<String> names, List<String> titles){
        DegreeDto dto = new DegreeDto(); 
        dto.setBaconDegree(degree);

        //storing name --> move each step in map entry and map represent the full path
        Map<String, String> steps = createNameAndTitleMap(names, titles);

        List<NameSmmary> namesSummary = namesSummaryRepository.getAllNamesFromIds(names); 
        List<TitleSummary> titlesSummary = titleSummaryRepository.getAllTitlesFromIds(titles);

        //fetching details path from from ids 
        List<DegreePathItem> paths = createFullPath(steps, namesSummary, titlesSummary);
        dto.setPath(paths);
        return dto; 
    }

    
    /** 
     * Create full path from NameTitleMap and nameSummaryList and titleSummaryList. 
     * 
     * @param steps
     * @param namesSummary
     * @param titlesSummary
     * @return List<DegreePathItem>
     */
    private List<DegreePathItem> createFullPath(Map<String, String> steps, List<NameSmmary> namesSummary, List<TitleSummary> titlesSummary) {
        DegreePathItem pathItem;
        List<DegreePathItem> paths = new ArrayList<>(); 
        for(Map.Entry<String,String> entry : steps.entrySet()){
            pathItem = new DegreePathItem(); 

            NameSmmary nm = namesSummary.stream().filter(nameSummmary -> nameSummmary.getNconst().equalsIgnoreCase(entry.getKey())).findFirst()
                                .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbI18NConstants.NAME_NOT_FOUND, entry.getKey())));    
            pathItem.setName(modelMapper.map(nm, NameDTO.class));
            
            if(null != entry.getValue()){
                TitleSummary tm = titlesSummary.stream().filter(titleSummmary -> titleSummmary.getTconst().equalsIgnoreCase(entry.getValue())).findFirst()
                                .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbI18NConstants.TITLE_NOT_FOUND, entry.getValue())));    
                pathItem.setTitle(modelMapper.map(tm, TitleDTO.class));
            }
            paths.add(pathItem); 
        }

        return paths; 
    }
    
    /** 
     * Create name and title map from nameIdsList and titleIdsList. 
     * 
     * @param names
     * @param titles
     * @return Map<String, String>
     */
    private Map<String, String> createNameAndTitleMap(List<String> names, List<String> titles) {
        Map<String,String> steps = new LinkedHashMap<>(); 
        int nameSize = names.size(); 
        for(int i= 0; i< nameSize; i++ ){
            if(i < nameSize - 1 ){
                steps.put(names.get(i), titles.get(i)); 
            }else {
                steps.put(names.get(i), null); 
            }
        }
        return steps;
    }

    /** 
     * Get all linked names from given 'title'.
     * 
     * @param title
     * @return Set<ActorInFilm>
     */
    private Set<ActorInFilm> getLinkedNameIds(String title){
        return titleService.getTitleWithPrincipalsById(title)
                    .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbI18NConstants.TITLE_NOT_FOUND, title)))
                    .getPrincipalsList().stream().map(principal ->  new ActorInFilm(principal.getId().getNconstId(), title)).collect(Collectors.toSet());
    }
    
    /** 
     * Get all linked titles from given 'name'.
     * 
     * @param name
     * @return Set<String>
     */
    private Set<String> getLinkedTitleIds(String name){
        return nameService.getNameById(name)
                    .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbI18NConstants.NAME_NOT_FOUND, name)))
                    .getPrincipalsList().stream().map(principal -> principal.getId().getTconstId()).collect(Collectors.toSet());
    }
    
}