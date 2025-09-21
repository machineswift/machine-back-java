package com.machine.sdk.common.envm.data.material;

import com.machine.sdk.common.envm.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataMaterIalImageFormatEnum implements BaseEnum<DataMaterIalImageFormatEnum, String> {
    JPG("JPG", "JPEG图像"),
    JPEG("JPEG", "JPEG图像"),
    PNG("PNG", "PNG图像"),
    GIF("GIF", "GIF图像"),
    WEBP("WEBP", "WebP图像"),
    SVG("SVG", "矢量图像"),
    BMP("BMP", "位图图像"),
    TIFF("TIFF", "TIFF图像"),
    ICO("ICO", "图标文件"),
    HEIF("HEIF", "高效图像格式"),
    HEIC("HEIC", "高效图像格式(苹果)"),
    RAW("RAW", "原始图像格式"),
    PSD("PSD", "Photoshop文件"),
    TGA("TGA", "Targa图像"),
    PDF("PDF", "PDF文档(可包含图像)");

    private final String code;
    private final String message;

    @Override
    public String getName() {
        return this.name();
    }
}