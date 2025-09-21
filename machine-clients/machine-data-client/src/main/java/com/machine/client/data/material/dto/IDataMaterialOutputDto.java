package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;

public interface IDataMaterialOutputDto {
    String getId();
    DataMaterIalTypeEnum getType();
    SystemStorageTypeEnum getStorageType();
    String getDescription();
    void setDescription(String description);

    // 元信息相关方法
    void setTextMetaInfo(DataMaterialTextDto textMetaInfo);
    void setImageMetaInfo(DataMaterialImageDto imageMetaInfo);
    void setAudioMetaInfo(DataMaterialAudioDto audioMetaInfo);
    void setVideoMetaInfo(DataMaterialVideoDto videoMetaInfo);
    void setDocumentMetaInfo(DataMaterialDocumentDto documentMetaInfo);
    void setFileMetaInfo(DataMaterialFileDto fileMetaInfo);
}
