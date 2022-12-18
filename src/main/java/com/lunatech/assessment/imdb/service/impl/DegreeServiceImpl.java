package com.lunatech.assessment.imdb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.Option;
import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.Title;
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
public class DegreeServiceImpl implements DegreeService{

    enum degreeItem {
        Name, Title
    }

    @Delegate private State state; 

    @Getter
    @Setter
    @AllArgsConstructor
    private class State{
        private int currentDegree=0;
        private List<String> visitedTitles= new ArrayList<>(); 
        private List<String> visitedNames = new ArrayList<>(); 
        //private Map<String,Boolean> cachedNamesResult = new HashMap<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class NameWithDegree{
        int degree; 
        String name; 
    }

    @Autowired
    private NameService nameService; 

    @Autowired
    private TitleService titleService;

    @Override
    public int getDegreeOfReachbetweenActors(String targetActor, String sourceActor) throws Exception {
        LinkedList<NameWithDegree> queue = new LinkedList<>(); 
        queue.add(new NameWithDegree(1, sourceActor)); 
        State searchState = new State(0, new ArrayList<>(), new ArrayList<>()); 
        Optional<State> resultState = degreeSearch(searchState, queue, targetActor); 
        
        return resultState.map(currentState -> currentState.getCurrentDegree()).orElse(-1); 
    }

    private Optional<State> degreeSearch(State state, LinkedList<NameWithDegree> queue, String targetName){

        if(queue.isEmpty()){
            return Optional.empty();
        }
        
        NameWithDegree element = queue.remove(); 
        String nextNameToTraverse = element.getName(); 
        
        //adding nodes in visiting list
        state.getVisitedNames().add(nextNameToTraverse);

        //Otherwise processing the name 
        List<String> linkedNames = getLinkedTitleIds(nextNameToTraverse)
            .stream()
            //intersecting currently all linked titles list with state visited list
            .filter(Predicate.not(state.getVisitedTitles()::contains))
            .filter(Objects::nonNull)
            .flatMap(title -> {
                state.getVisitedTitles().add(title); 
                return getLinkedNameIds(title).stream(); 
                })
            .collect(Collectors.toList()); 

        System.out.println("DDDDDDDDDDDD------- Link names size "+linkedNames.size() +" nextName "+nextNameToTraverse);

        boolean nameResult = linkedNames.stream().anyMatch(linkedName -> linkedName.equalsIgnoreCase(targetName));
        
        //adding result into cache 
        //state.getCachedNamesResult().put(nextNameToTraverse, nameResult); 
        
        //start searching for target name from one degree linked names to next degree if not found. 
        if(!nameResult){
            //adding current degree plus One with all linked name to be seached later from queue. 
            queue.addAll(linkedNames.stream()
                                .filter(Predicate.not(state.getVisitedNames()::contains))
                                .filter(Objects::nonNull)
                                .map(linkedName -> new NameWithDegree(element.getDegree() + 1, linkedName))
                                .collect(Collectors.toList())); 

            System.out.println("DDDDDDDDDDDD------- QUEUE names size "+queue.size());
            return degreeSearch(state, queue, targetName); 
        }
        state.setCurrentDegree(element.getDegree());
        return Optional.of(state); 
    }

    private Set<String> getLinkedNameIds(String title){
        return titleService.getTitleById(title).orElseThrow().getPrincipalsList().stream().map(principal -> principal.getId().getNconstId()).collect(Collectors.toSet());
    }

    private Set<String> getLinkedTitleIds(String name){
        return nameService.getNameById(name).orElseThrow().getPrincipalsList().stream().map(principal -> principal.getId().getTconstId()).collect(Collectors.toSet());
    }
    
}