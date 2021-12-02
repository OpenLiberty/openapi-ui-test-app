package io.openliberty.openapi.test.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecordStore {
    
    private Map<Integer, ExampleRecord> records;
    
    @PostConstruct
    private void setup() {
        records = Collections.synchronizedMap(new HashMap<>());
    }
    
    public void store(ExampleRecord record) {
        records.put(record.id, record);
    }
    
    public void delete(int id) {
        records.remove(id);
    }
    
    public ExampleRecord get(int id) {
        return records.get(id);
    }
    
    public List<ExampleRecord> getAll() {
        synchronized (records) {
            return new ArrayList<>(records.values());
        }
    }
}
