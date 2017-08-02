package com.dylan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.INITIALIZE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static com.dylan.Status.INITIAL;

public class Creator {
    int counter = 0;
    public void createWorkOrders() {
        // read input, create work orders and write as json files
        //set an id when the order is created,persist the order to
        //a file in JSON with id as its name.
        WorkOrder workOrder = new WorkOrder();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int iD = counter++;
            workOrder.setiD(iD);
            System.out.println("What is your problem");
            String description = scanner.next();
            workOrder.setDescription(description);
            System.out.println("What is your name?");
            String name = scanner.next();
            workOrder.setSenderName(name);
            workOrder.setStatus(INITIAL);

            try {
                File file = new File(String.valueOf(workOrder.getiD()) + ".json");
                FileWriter fw = new FileWriter(file);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(workOrder);
                fw.write(json);
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}
