package com.machine.sdk.common.envm.data.material;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterialAudioFormatEnum implements BaseEnum<DataMaterialAudioFormatEnum, String> {
    MP3("MP3", "MPEG音频层3"),
    WAV("WAV", "WAVE音频格式"),
    AAC("AAC", "高级音频编码"),
    OGG("OGG", "Ogg Vorbis音频"),
    FLAC("FLAC", "无损音频编解码器"),
    M4A("M4A", "MPEG-4音频"),
    WMA("WMA", "Windows媒体音频"),
    AIFF("AIFF", "音频交换文件格式"),
    AMR("AMR", "自适应多速率音频编解码器"),
    APE("APE", "Monkey's Audio无损格式"),
    OPUS("OPUS", "Opus音频格式"),
    MIDI("MIDI", "MIDI音乐文件"),
    AC3("AC3", "杜比数字音频"),
    DTS("DTS", "数字影院系统音频"),
    RA("RA", "RealAudio格式"),
    AU("AU", "Sun/NeXT音频格式"),
    VOC("VOC", "Creative声音文件"),
    SPX("SPX", "Speex音频格式"),
    CAF("CAF", "苹果核心音频格式"),
    GSM("GSM", "GSM 06.10音频");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}