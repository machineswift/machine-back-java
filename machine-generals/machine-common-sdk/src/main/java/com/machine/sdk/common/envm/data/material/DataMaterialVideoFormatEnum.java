package com.machine.sdk.common.envm.data.material;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterialVideoFormatEnum implements BaseEnum<DataMaterialVideoFormatEnum, String> {
    MP4("MP4", "MPEG-4视频格式"),
    AVI("AVI", "音频视频交错格式"),
    MOV("MOV", "QuickTime影片格式"),
    WMV("WMV", "Windows媒体视频"),
    FLV("FLV", "Flash视频格式"),
    MKV("MKV", "Matroska多媒体容器"),
    WEBM("WEBM", "WebM开放视频格式"),
    MPEG("MPEG", "MPEG视频格式"),
    _3GP("3GP", "3GPP移动视频格式"),
    M4V("M4V", "iTunes视频格式"),
    F4V("F4V", "Adobe Flash视频"),
    VOB("VOB", "DVD视频对象"),
    RMVB("RMVB", "RealMedia可变比特率"),
    TS("TS", "MPEG传输流"),
    M2TS("M2TS", "蓝光BDAV视频"),
    DIVX("DIVX", "DivX编码视频"),
    XVID("XVID", "Xvid编码视频"),
    H264("H264", "H.264编码视频"),
    H265("H265", "H.265/HEVC编码视频"),
    VP8("VP8", "VP8编码视频"),
    VP9("VP9", "VP9编码视频"),
    AV1("AV1", "AOMedia视频1格式"),
    GIF("GIF", "图形交换格式动画"),
    APNG("APNG", "动画PNG格式");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}