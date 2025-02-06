package com.machine.client.iam.user.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserAuthDetailOutputDto {
    private String userId;

    private String userName;

    private String password;

    private String name;

    private String phone;

    private StatusEnum status;

    private List<String> roleCodeList;

    private List<String> permissionCodeList;

    public void addPermissionCode(String permissionCode) {
        if (permissionCodeList == null) {
            permissionCodeList = new ArrayList<String>();
        }
        permissionCodeList.add(permissionCode);
    }
}
