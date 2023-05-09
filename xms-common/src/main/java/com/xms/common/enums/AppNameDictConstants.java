package com.xms.common.enums;

/**
 * 天行平台各APP名字枚举类
 *
 * @Title: AppNameDictConstants.java
 * @Description: 天行平台各APP名字枚举类
 * @author: xms
 * @date: 2018年3月14日 下午3:44:38
 */
public enum AppNameDictConstants {

    Apollo("内容生产"),
    Mars("任务调度"),
    MSF("配置中心"),
    Alkaid("转码技审打包调度"),
    FT("迁移服务"),
    Mantis("自动拆条服务"),
    FastCarve("快编"),
    TianXing("门户"),
    Sirius("收录"),
    Horus("智能审核"),
    VSM("查重去重"),
    UU("统一认证中心"),
    NutsCMS("CMS"),
    GooSpider("爬虫系统"),
    HeavenNote("统一日志中心"),
    Dipper("人物库"),
    MConf("蚂蚁配置中心"),
    MediaIngest("上载客户端"),
    MediaDownload("下载客户端"),
    AndroidApp("安卓App"),
    IOSApp("iosApp"),
    IdeaBoard("H5编译器"),
    DeleteService("删除服务"),
	Brighteyes("明眸眼睛"),
	Dor("数字化手术室"),
	Livemonitor("场记直播"),
    Nmp("国际世通"),
	HierarchicalStorage("分级存储"),
	AudioBooks("有声读物");
    private String description = "";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    AppNameDictConstants(String description) {
        this.description = description;
    }
}
