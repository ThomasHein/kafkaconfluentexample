package com.example.demokafkaproducer;

import com.example.demokafkaproducer.kafkaexamples.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaEndlessWriterProcessesAsync {

    private EndlessDbWriter3 endlessDbWriter3;

    @Autowired
    public KafkaEndlessWriterProcessesAsync(EndlessDbWriter3 endlessDbWriter3) {
        this.endlessDbWriter3 = endlessDbWriter3;
    }

    public void startEndlessWriter() {

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

        Runnable taskTransactionAvroOrder = () -> {
            new OrderAvroWriter5().endlessOrderWriter();
        };

        Thread thPersonWriter = new Thread(taskPersonWriter);
        Thread thOrderWriter = new Thread(taskOrder);
        Thread thDatabaseWriter = new Thread(taskDb);
        Thread thTransactionWriter = new Thread(taskTransaction);
        Thread th5AvroWriter = new Thread(taskTransactionAvroOrder);
        thPersonWriter.start();
        // thOrderWriter.start();
        // thDatabaseWriter.start();
        // thTransactionWriter.start();
        // th5AvroWriter.start();

    }

}
