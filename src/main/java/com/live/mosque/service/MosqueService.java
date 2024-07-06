package com.live.mosque.service;

import com.live.mosque.data.Mosque;
import com.live.mosque.item.MosquItem;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.live.mosque.item.MosquItem.mosqueTableSchema;

@Service
@Slf4j
public class MosqueService {

    @Resource
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    public List<Mosque> getAllMosque() {
        return List.of();
    }

    public void addMosque(Mosque mosque) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);

        try {
            MosquItem mosquItem = new MosquItem();
            mosquItem.setPk(mosque.getUid());
            mosquItem.setSk(mosque.getUid());
            mosquItem.setMeta(mosque.getMeta());

            mosquItemDynamoDbAsyncTable.putItem(mosquItem).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Mosque patchUpdate(Mosque mosque) {
        return null;
    }


    public Mosque putUpdate(Mosque mosque) {
        return null;
    }

    public Mosque findByKey(String uid) {
        return null;
    }

    public void delete(String uid) {
        final DynamoDbAsyncTable<MosquItem> mosquItemDynamoDbAsyncTable =
                dynamoDbEnhancedAsyncClient.table("mosque", mosqueTableSchema);
        mosquItemDynamoDbAsyncTable.deleteItem(Key.builder().partitionValue(uid).build());
    }
}
