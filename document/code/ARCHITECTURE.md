
### 工程分层

```
APP 服务层级规范
├── role
│   └── controllerr
│   │   └── vo
│   │   │   └── request
│   │   │   │  └── IamRoleCreateRequestVo.java
│   │   │   └── response
│   │   │      └── IamRoleDetailResponseVo.java
│   │   └──IamRoleController.java
│   └── business
│   │  └── bo
│   │  │  └── IamRoleBo.java
│   │  │  └── impl
│   │  │  │  └── IamRoleBusinessImpl.java
│   │  └── IIamRoleBusiness.java


CLIENT 服务层级规范
├── role
│   └── dto
│   │   └── input
│   │   │  └── PermissionCreateInputDto.java
│   │   └── output
│   │   │      └── PermissionDetailOutputDto.java
│   └── IPermissionClient.java


SERVICE 服务层级规范
├── role
│   └── server
│   │   └── RoleServer.java
│   └── service
│   │   └── bo
│   │   │   └── RoleXXBo.java
│   │   └── impl
│   │   │   └── RoleServiceImpl.java
│   │   └──IRoleService.java
│   └── dao
│   │   └── po
│   │   │   └── RoleSimplePo.java
│   │   └── impl
│   │   │   └── RoleDaoImpl.java
│   │   └── maapper
│   │   │  └── entity
│   │   │  │  └── RoleEntity.java
│   │   └── IRoleMapper.java

```

### 设计规范
```
主键ID用String类型，为32位的UUID。
时间用Long类型，Unix 时间戳。
```


### 附件文件夹规范
```
ERP 附件
├── 基础数据(base)
│   └── XXX
│   │   └── XXX
│   │   │   └── XXX
│   │   │   │  └── XXX
│   │   │   └── XXX
│   │   │      └── XXX
├── 运营中心(oc)
│   └── XXX
│   │   └── XXX
│   │   │  └── XXX
│   │   └── XXX
│   │   │      └── XXX
├── 营销中心(mc)
│   └── XXX
│   │   └── XXX
│   │   │  └── XXX
│   │   └── output
│   │   │      └── XXX
├──  供应链中心(hcm)
│   └── XXX
│   │   └── XXX
│   │   │  └── XXX
│   │   └── XXX
│   │   │      └── XXX
├── 下载中心(download)
│   └── uuid
│   │   └── filename
```