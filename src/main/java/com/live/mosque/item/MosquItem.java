package com.live.mosque.item;

import com.live.mosque.converter.MapTypeConverter;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Map;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;
import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primarySortKey;

@Data
public class MosquItem implements Comparable<MosquItem> {

    public static final TableSchema<MosquItem> mosqueTableSchema =
            TableSchema.builder(MosquItem.class)
                    .newItemSupplier(MosquItem::new)
                    .addAttribute(String.class, a -> a.name("PK")
                            .getter(MosquItem::getPk)
                            .setter(MosquItem::setPk)
                            .tags(primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("SK")
                            .getter(MosquItem::getSk)
                            .setter(MosquItem::setSk)
                            .tags(primarySortKey()))
                    .addAttribute(Map.class, a -> a.name("meta")
                            .getter(MosquItem::getMeta)
                            .setter(MosquItem::setMeta).attributeConverter(new MapTypeConverter())).build();

    private String pk;
    private String sk;
    private Map<String, Object> meta;

    @Override
    public int compareTo(MosquItem o) {
        return this.getPk().compareTo(o.getPk());
    }
}
