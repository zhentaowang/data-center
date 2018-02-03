数据中心项目
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alibaba/druid-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alibaba/druid-spring-boot-starter/)

## 创建目的
提供一个稳定，健全的航班信息数据

## 注意问题
1. 飞常准白名单问题
在k8s集群上运行的应用，在调用飞常准接口时是根据应用所在的服务器IP去请求的，因删除POD，应用运行的服务器会变更，固要把集群跑应用的所有服务器的公网IP都加入飞常准的访问白名单


