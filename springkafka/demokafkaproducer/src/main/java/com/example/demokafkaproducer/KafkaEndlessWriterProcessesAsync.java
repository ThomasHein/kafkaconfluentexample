package com.example.demokafkaproducer;

import com.example.demokafkaproducer.kafkaexamples.EndlessDbWriter3;
import com.example.demokafkaproducer.kafkaexamples.EndlessOrderWriter1;
import com.example.demokafkaproducer.kafkaexamples.EndlessPersonWriter2;
import com.example.demokafkaproducer.kafkaexamples.OrderShippingTransaction4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaEndlessWriterProcessesAsync {


    private EndlessDbWriter3 endlessDbWriter3;

    @Autowired
    public KafkaEndlessWriterProcessesAsync(EndlessDbWriter3 endlessDbWriter3){
        this.endlessDbWriter3 = endlessDbWriter3;
    }



    public void startEndlessWriter(){

        Runnable taskOrder = () -> {
            new EndlessOrderWriter1().endlessOrderWriter();
        };

        Runnable taskPersonWriter = () -> {
            new EndlessPersonWriter2().endlessPersonWriter();
        };

        Runnable taskDb = () -> {
            this.endlessDbWriter3.write();
        };

        Runnable taskTransaction = () -> {
            new OrderShippingTransaction4().sendOrderShippingTransaction();
        };



        Thread th = new Thread(taskPersonWriter);
        Thread th2 = new Thread(taskOrder);
        Thread th3 = new Thread(taskDb);
        Thread th4 = new Thread(taskTransaction);
        th.start();
        //th2.start();
        //th3.start();
        //th4.start();

    }


}
