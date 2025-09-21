package com.machine.service.data.leaf.service;

public interface IDataLeafService {

    String getKqBatchNo(String category,
                        String prefix,
                        int length,
                        int step,
                        String remark);


    Long getLeafId(String bizTag,
                   Integer step,
                   Long expireTime,
                   String remark);
}
