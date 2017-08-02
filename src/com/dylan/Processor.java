package com.dylan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.classfile.Opcode;
import org.omg.CORBA.INITIALIZE;
import sun.font.TrueTypeFont;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Processor {
    private Map<Status, Set> workOrderMap = new HashMap<>();
    public Processor() {
        Set<WorkOrder> initial = new HashSet<>();
        Set<WorkOrder> assinged = new HashSet<>();
        Set<WorkOrder> inProgress = new HashSet<>();
        Set<WorkOrder> done = new HashSet<>();
        workOrderMap.put(Status.INITIAL, initial);
        workOrderMap.put(Status.ASSIGNED, assinged);
        workOrderMap.put(Status.IN_PROGRESS, inProgress);
        workOrderMap.put(Status.DONE, done);
    }

    public void processWorkOrders() {
        while (true) {
            moveIt();
            readIt();
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveIt() {
        // move work orders in map from one state to another
        Set<WorkOrder> initial = this.workOrderMap.get(Status.INITIAL);
        Set<WorkOrder> assigned = this.workOrderMap.get(Status.ASSIGNED);
        Set<WorkOrder> inProgress = this.workOrderMap.get(Status.IN_PROGRESS);
        Set<WorkOrder> done = this.workOrderMap.get(Status.DONE);
        ObjectMapper mapper = new ObjectMapper();
        for(WorkOrder workOrder : inProgress){
            workOrder.setStatus(Status.DONE);
            done.add(workOrder);
        }
        inProgress.clear();
        workOrderMap.put(Status.IN_PROGRESS, inProgress);
        for (WorkOrder workOrder : assigned){
            workOrder.setStatus(Status.IN_PROGRESS);
            inProgress.add(workOrder);
        }
        assigned.clear();
        workOrderMap.put(Status.ASSIGNED, assigned);
        for(WorkOrder workOrder : initial){
            workOrder.setStatus(Status.ASSIGNED);
            assigned.add(workOrder);
        }
        initial.clear();
        workOrderMap.put(Status.INITIAL, initial);
        try {
            System.out.println(mapper.writeValueAsString(workOrderMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    private void readIt() {
        // read the json files into WorkOrders and put in map
        Set<WorkOrder> initial = this.workOrderMap.get(Status.INITIAL);
        File file = new File(".");
        File files[] = file.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                // f is a reference to a json file
                ObjectMapper mapper = new ObjectMapper();
                WorkOrder wo;
                try {
                    wo = mapper.readValue(f, WorkOrder.class);
//                    System.out.println(wo.getiD());
//                    System.out.println(wo.getSenderName());
//                    System.out.println(wo.getDescription());
//                    System.out.println(wo.getStatus());
//                    System.out.println(mapper.writeValueAsString(wo));
                    initial.add(wo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                f.delete();
            }
        }
        System.out.println("five seconds");

    }

    public static void main(String args[]) {

        Processor processor = new Processor();
        processor.processWorkOrders();
    }
}
