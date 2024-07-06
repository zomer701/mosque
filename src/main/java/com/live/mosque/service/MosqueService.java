package com.live.mosque.service;

import com.live.mosque.data.Mosque;
import com.live.mosque.item.MosquItem;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.live.mosque.item.MosquItem.mosqueTableSchema;

@Service
@Slf4j
public class MosqueService {

    @Resource
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    public List<Mosque> getAllMosque(int count) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);

        List<Mosque> resultData = new ArrayList<>();

        mosquItemDynamoDbAsyncTable.scan().items().limit(count)
                .subscribe(product -> {
                      Mosque mosque = new Mosque();
                      mosque.setUid(product.getPk());
                      mosque.setMeta(product.getMeta());
                    resultData.add(mosque);
                })
                .exceptionally(failure -> {
                    log.error("ERROR  - ", failure);
                    return null;
                })
                .join(); // If needed, blocks the subscribe() method thread until it is finished processing.

        return resultData;
    }

    public void addMosque(Mosque mosque) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);

        try {
            MosquItem mosquItem = new MosquItem();
            mosquItem.setPk(mosque.getUid());
            mosquItem.setSk("#");
            mosquItem.setMeta(Optional.ofNullable(mosque.getMeta()).orElse(new HashMap<>()));

            mosquItemDynamoDbAsyncTable.putItem(mosquItem).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Mosque patchUpdate(Mosque mosque) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);
        try {
            MosquItem item = mosquItemDynamoDbAsyncTable.getItem(Key.builder().partitionValue(mosque.getUid())
                    .sortValue("#").build()).get();

            item.setMeta(Optional.ofNullable(mosque.getMeta()).orElse(new HashMap<>()));
            mosquItemDynamoDbAsyncTable.putItem(item).get();
            return mosque;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Mosque putUpdate(Mosque mosque) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);
        try {
            MosquItem item = mosquItemDynamoDbAsyncTable.getItem(Key.builder().partitionValue(mosque.getUid())
                    .sortValue("#").build()).get();
            Map<String, Object> date = item.getMeta();

            Map<String, Object> current = Optional.ofNullable(mosque.getMeta()).orElse(new HashMap<>());

            for (String key: current.keySet()) {
                date.put(key, current.get(key));
            }

            item.setMeta(date);

            mosquItemDynamoDbAsyncTable.putItem(item).get();

            mosque.setMeta(date);
            return mosque;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Mosque findByKey(String uid) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);
        try {
            MosquItem item = mosquItemDynamoDbAsyncTable.getItem(Key.builder().partitionValue(uid).sortValue("#").build()).get();
            Mosque mosque = new Mosque();
            mosque.setUid(item.getPk());
            mosque.setMeta(item.getMeta());

            return mosque;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void delete(String uid) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);
        mosquItemDynamoDbAsyncTable.deleteItem(Key.builder().partitionValue(uid).sortValue("#").build()).get();
    }
}
