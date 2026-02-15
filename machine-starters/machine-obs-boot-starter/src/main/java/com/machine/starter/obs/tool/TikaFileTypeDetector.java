package com.machine.starter.obs.tool;

import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.parser.Parser;
import org.apache.tika.config.TikaConfig;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tika 文件类型检测工具类
 * 支持 MIME 类型检测、文件分类、元数据提取等功能
 * 同时支持 File 和 Spring MultipartFile
 */
public class TikaFileTypeDetector {

    private static volatile TikaFileTypeDetector instance;
    private final Tika tika;
    private final TikaConfig tikaConfig;
    private final Map<String, DataFileTypeEnum> mimeTypeToCategoryCache;

    // MIME 类型到分类的映射
    private static final Map<String, DataFileTypeEnum> MIME_TYPE_CATEGORY_MAP = new HashMap<>();

    static {
        // 图片类型
        addMimeTypes("image/jpeg,image/png,image/gif,image/bmp,image/webp,image/svg+xml,image/tiff,image/x-icon",
                DataFileTypeEnum.IMAGE);

        // 视频类型
        addMimeTypes("video/mp4,video/avi,video/x-msvideo,video/quicktime,video/x-matroska,video/x-ms-wmv,video/x-flv,video/mpeg",
                DataFileTypeEnum.VIDEO);

        // 音频类型
        addMimeTypes("audio/mpeg,audio/wav,audio/x-wav,audio/flac,audio/aac,audio/ogg,audio/x-ms-wma,audio/midi,audio/x-midi",
                DataFileTypeEnum.AUDIO);

        // 文档类型
        addMimeTypes("application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document," +
                        "text/plain,text/rtf,application/rtf,application/vnd.oasis.opendocument.text,text/markdown,text/x-markdown",
                DataFileTypeEnum.DOCUMENT);

        // 电子表格
        addMimeTypes("application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet," +
                        "text/csv,application/vnd.oasis.opendocument.spreadsheet",
                DataFileTypeEnum.SPREADSHEET);

        // 演示文稿
        addMimeTypes("application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation," +
                        "application/vnd.oasis.opendocument.presentation",
                DataFileTypeEnum.PRESENTATION);

        // 压缩文件
        addMimeTypes("application/zip,application/x-rar-compressed,application/x-7z-compressed,application/x-tar," +
                        "application/gzip,application/x-bzip2,application/x-xz,application/x-compress",
                DataFileTypeEnum.ARCHIVE);

        // 可执行文件
        addMimeTypes("application/x-msdownload,application/x-ms-installer,application/vnd.android.package-archive," +
                        "application/x-executable,application/x-mach-binary",
                DataFileTypeEnum.EXECUTABLE);

        // 源代码
        addMimeTypes("text/x-java-source,text/x-python,text/javascript,text/x-c,text/x-c++,text/x-csharp,text/x-php," +
                        "text/x-ruby,text/x-go,text/x-swift",
                DataFileTypeEnum.CODE);

        // 配置文件
        addMimeTypes("application/xml,text/xml,application/json,text/yaml,text/x-yaml,text/x-properties",
                DataFileTypeEnum.CONFIG);

        // 字体文件
        addMimeTypes("font/ttf,font/otf,font/woff,font/woff2,application/vnd.ms-fontobject",
                DataFileTypeEnum.FONT);

        // 3D模型
        addMimeTypes("application/sla,model/stl,model/obj,application/vnd.ms-pki.stl,model/x.stl-ascii,model/x.stl-binary",
                DataFileTypeEnum.MODEL_3D);

        // CAD文件
        addMimeTypes("application/acad,image/vnd.dwg,application/dxf",
                DataFileTypeEnum.CAD);

        // 电子书
        addMimeTypes("application/epub+zip,application/x-mobipocket-ebook,application/vnd.amazon.ebook",
                DataFileTypeEnum.E_BOOK);

        // 数据库
        addMimeTypes("application/x-sqlite3,application/vnd.ms-access,application/x-msaccess",
                DataFileTypeEnum.DATABASE);

        // 日志文件
        addMimeTypes("text/x-log,application/log",
                DataFileTypeEnum.LOG);

        // 系统文件
        addMimeTypes("application/x-dll,application/x-sharedlib,application/x-ms-shortcut",
                DataFileTypeEnum.SYSTEM);
    }

    private static void addMimeTypes(String mimeTypes, DataFileTypeEnum category) {
        String[] types = mimeTypes.split(",");
        for (String type : types) {
            MIME_TYPE_CATEGORY_MAP.put(type.trim(), category);
        }
    }

    private TikaFileTypeDetector() {
        this.tika = new Tika();
        this.tikaConfig = TikaConfig.getDefaultConfig();
        this.mimeTypeToCategoryCache = new ConcurrentHashMap<>();
    }

    public static TikaFileTypeDetector getInstance() {
        if (instance == null) {
            synchronized (TikaFileTypeDetector.class) {
                if (instance == null) {
                    instance = new TikaFileTypeDetector();
                }
            }
        }
        return instance;
    }

    // ==================== File 相关方法 ====================

    /**
     * 检测文件的 MIME 类型
     */

    @SneakyThrows
    public String detectMimeType(File file) {
        try (InputStream is = TikaInputStream.get(file.toPath())) {
            return tika.detect(is);
        }
    }

    /**
     * 检测文件的 MIME 类型（从输入流）
     */
    @SneakyThrows
    public String detectMimeType(InputStream inputStream, String fileName) {
        return tika.detect(inputStream, fileName);
    }

    /**
     * 检测文件的 MIME 类型（从字节数组）
     */
    public String detectMimeType(byte[] data, String fileName) {
        return tika.detect(data, fileName);
    }

    /**
     * 获取文件分类
     */
    public DataFileTypeEnum getFileType(File file) {
        String mimeType = detectMimeType(file);
        return getCategoryFromMimeType(mimeType);
    }

    /**
     * 提取文件元数据
     */
    @SneakyThrows
    public Metadata extractMetadata(File file) {
        Metadata metadata = new Metadata();
        try (InputStream is = TikaInputStream.get(file.toPath())) {
            Parser parser = new AutoDetectParser(tikaConfig.getDetector());
            ContentHandler handler = new BodyContentHandler(-1);
            ParseContext context = new ParseContext();
            parser.parse(is, handler, metadata, context);
        }
        return metadata;
    }

    /**
     * 提取文件内容（文本）
     */
    @SneakyThrows
    public String extractContent(File file)  {
        try (InputStream is = TikaInputStream.get(file.toPath())) {
            return tika.parseToString(is);
        }
    }

    /**
     * 获取文件的详细类型信息
     */
    public FileTypeInfo getFileTypeInfo(File file) {

        String mimeType = detectMimeType(file);
        DataFileTypeEnum category = getCategoryFromMimeType(mimeType);
        Metadata metadata = extractMetadata(file);

        return new FileTypeInfo(
                file.getName(),
                file.getPath(),
                mimeType,
                category,
                file.length(),
                metadata
        );
    }

    // ==================== MultipartFile 相关方法 ====================

    /**
     * 检测 MultipartFile 的 MIME 类型
     */
    @SneakyThrows
    public String detectMimeType(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return tika.detect(is, file.getOriginalFilename());
        }
    }

    /**
     * 检测 MultipartFile 的 MIME 类型（字节数组方式）
     */
    @SneakyThrows
    public String detectMimeType(MultipartFile file, boolean useBytes) {
        if (useBytes) {
            return tika.detect(file.getBytes(), file.getOriginalFilename());
        } else {
            return detectMimeType(file);
        }
    }

    /**
     * 获取 MultipartFile 的文件分类
     */
    public DataFileTypeEnum getFileType(MultipartFile file) {
        String mimeType = detectMimeType(file);
        return getCategoryFromMimeType(mimeType);
    }

    /**
     * 提取 MultipartFile 的元数据
     */
    @SneakyThrows
    public Metadata extractMetadata(MultipartFile file) {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.CONTENT_LENGTH, String.valueOf(file.getSize()));

        try (InputStream is = TikaInputStream.get(file.getInputStream())) {
            AutoDetectParser parser = new AutoDetectParser(tikaConfig.getDetector());
            BodyContentHandler handler = new BodyContentHandler(-1);
            ParseContext context = new ParseContext();
            parser.parse(is, handler, metadata, context);
        }
        return metadata;
    }

    /**
     * 提取 MultipartFile 的内容（文本）
     */
    @SneakyThrows
    public String extractContent(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return tika.parseToString(is);
        }
    }

    /**
     * 获取 MultipartFile 的详细类型信息
     */
    public MultipartFileInfo getMultipartFileInfo(MultipartFile file) {
        String mimeType = detectMimeType(file);
        DataFileTypeEnum category = getCategoryFromMimeType(mimeType);
        Metadata metadata = extractMetadata(file);

        return new MultipartFileInfo(
                file.getOriginalFilename(),
                file.getSize(),
                mimeType,
                category,
                file.getContentType(),
                metadata
        );
    }

    // ==================== 通用方法 ====================

    /**
     * 根据 MIME 类型获取文件分类
     */
    public DataFileTypeEnum getCategoryFromMimeType(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return DataFileTypeEnum.UNKNOWN;
        }

        // 检查缓存
        if (mimeTypeToCategoryCache.containsKey(mimeType)) {
            return mimeTypeToCategoryCache.get(mimeType);
        }

        // 精确匹配
        DataFileTypeEnum category = MIME_TYPE_CATEGORY_MAP.get(mimeType);
        if (category != null) {
            mimeTypeToCategoryCache.put(mimeType, category);
            return category;
        }

        // 前缀匹配（例如：image/* 匹配所有图片类型）
        for (Map.Entry<String, DataFileTypeEnum> entry : MIME_TYPE_CATEGORY_MAP.entrySet()) {
            String key = entry.getKey();
            if (key.contains("/*")) {
                String prefix = key.substring(0, key.indexOf("/*"));
                if (mimeType.startsWith(prefix)) {
                    category = entry.getValue();
                    mimeTypeToCategoryCache.put(mimeType, category);
                    return category;
                }
            }
        }

        // 如果没找到匹配，根据 MIME 类型前缀判断
        category = inferCategoryFromMimeTypePrefix(mimeType);
        mimeTypeToCategoryCache.put(mimeType, category);
        return category;
    }

    /**
     * 从 MIME 类型前缀推断分类
     */
    private DataFileTypeEnum inferCategoryFromMimeTypePrefix(String mimeType) {
        if (mimeType == null || mimeType.isEmpty()) {
            return DataFileTypeEnum.UNKNOWN;
        }

        if (mimeType.startsWith("image/")) {
            return DataFileTypeEnum.IMAGE;
        } else if (mimeType.startsWith("video/")) {
            return DataFileTypeEnum.VIDEO;
        } else if (mimeType.startsWith("audio/")) {
            return DataFileTypeEnum.AUDIO;
        } else if (mimeType.startsWith("text/")) {
            // 文本文件可能是文档、代码或配置
            if (mimeType.contains("html") || mimeType.contains("xml") ||
                    mimeType.contains("json") || mimeType.contains("yaml") ||
                    mimeType.contains("properties")) {
                return DataFileTypeEnum.CONFIG;
            } else if (mimeType.contains("java") || mimeType.contains("python") ||
                    mimeType.contains("javascript") || mimeType.contains("c") ||
                    mimeType.contains("php") || mimeType.contains("ruby")) {
                return DataFileTypeEnum.CODE;
            } else {
                return DataFileTypeEnum.DOCUMENT;
            }
        } else if (mimeType.startsWith("application/")) {
            if (mimeType.contains("pdf") || mimeType.contains("msword") ||
                    mimeType.contains("opendocument") || mimeType.contains("rtf")) {
                return DataFileTypeEnum.DOCUMENT;
            } else if (mimeType.contains("excel") || mimeType.contains("spreadsheet")) {
                return DataFileTypeEnum.SPREADSHEET;
            } else if (mimeType.contains("powerpoint") || mimeType.contains("presentation")) {
                return DataFileTypeEnum.PRESENTATION;
            } else if (mimeType.contains("zip") || mimeType.contains("rar") ||
                    mimeType.contains("tar") || mimeType.contains("gzip") ||
                    mimeType.contains("compress")) {
                return DataFileTypeEnum.ARCHIVE;
            } else if (mimeType.contains("executable") || mimeType.contains("installer") ||
                    mimeType.contains("package")) {
                return DataFileTypeEnum.EXECUTABLE;
            }
        } else if (mimeType.startsWith("font/")) {
            return DataFileTypeEnum.FONT;
        } else if (mimeType.startsWith("model/")) {
            return DataFileTypeEnum.MODEL_3D;
        }

        return DataFileTypeEnum.UNKNOWN;
    }

    // ==================== 文件类型检查方法 ====================

    /**
     * 检查文件是否是图片
     */
    public boolean isImage(File file)  {
        return getFileType(file) == DataFileTypeEnum.IMAGE;
    }

    /**
     * 检查 MultipartFile 是否是图片
     */
    public boolean isImage(MultipartFile file) {
        return getFileType(file) == DataFileTypeEnum.IMAGE;
    }

    /**
     * 检查文件是否是视频
     */
    public boolean isVideo(File file)  {
        return getFileType(file) == DataFileTypeEnum.VIDEO;
    }

    /**
     * 检查 MultipartFile 是否是视频
     */
    public boolean isVideo(MultipartFile file) {
        return getFileType(file) == DataFileTypeEnum.VIDEO;
    }

    /**
     * 检查文件是否是音频
     */
    public boolean isAudio(File file)  {
        return getFileType(file) == DataFileTypeEnum.AUDIO;
    }

    /**
     * 检查 MultipartFile 是否是音频
     */
    public boolean isAudio(MultipartFile file)  {
        return getFileType(file) == DataFileTypeEnum.AUDIO;
    }

    /**
     * 检查文件是否是文档
     */
    public boolean isDocument(File file)  {
        DataFileTypeEnum category = getFileType(file);
        return category == DataFileTypeEnum.DOCUMENT ||
                category == DataFileTypeEnum.SPREADSHEET ||
                category == DataFileTypeEnum.PRESENTATION ||
                category == DataFileTypeEnum.E_BOOK;
    }

    /**
     * 检查 MultipartFile 是否是文档
     */
    public boolean isDocument(MultipartFile file)  {
        DataFileTypeEnum category = getFileType(file);
        return category == DataFileTypeEnum.DOCUMENT ||
                category == DataFileTypeEnum.SPREADSHEET ||
                category == DataFileTypeEnum.PRESENTATION ||
                category == DataFileTypeEnum.E_BOOK;
    }

    /**
     * 检查 MultipartFile 是否是允许的类型
     */
    public boolean isAllowedType(MultipartFile file,
                                 Set<DataFileTypeEnum> allowedCategories)  {
        DataFileTypeEnum category = getFileType(file);
        return allowedCategories.contains(category);
    }

    /**
     * 检查 MultipartFile 是否是指定的 MIME 类型
     */
    public boolean isSpecificType(MultipartFile file, String expectedMimeType)  {
        String actualMimeType = detectMimeType(file);
        return actualMimeType.equalsIgnoreCase(expectedMimeType);
    }

    // ==================== 批量处理方法 ====================

    /**
     * 批量检测文件类型
     */
    public List<FileTypeInfo> batchDetectFiles(List<File> files) {
        List<FileTypeInfo> results = new ArrayList<>();
        for (File file : files) {

                results.add(getFileTypeInfo(file));

        }
        return results;
    }

    /**
     * 批量处理 MultipartFile 列表
     */
    public List<MultipartFileInfo> batchProcessFiles(List<MultipartFile> files) {
        List<MultipartFileInfo> results = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {

                    results.add(getMultipartFileInfo(file));

            }
        }
        return results;
    }

    // ==================== 实用方法 ====================

    /**
     * 过滤指定类型的文件
     */
    public List<MultipartFile> filterFilesByCategory(List<MultipartFile> files, DataFileTypeEnum category){
        List<MultipartFile> filtered = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty() && getFileType(file) == category) {
                filtered.add(file);
            }
        }
        return filtered;
    }

    /**
     * 获取所有支持的 MIME 类型
     */
    public Set<String> getSupportedMimeTypes() {
        return new HashSet<>(MIME_TYPE_CATEGORY_MAP.keySet());
    }

    /**
     * 获取指定分类的所有 MIME 类型
     */
    public List<String> getMimeTypesByCategory(DataFileTypeEnum category) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, DataFileTypeEnum> entry : MIME_TYPE_CATEGORY_MAP.entrySet()) {
            if (entry.getValue() == category) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    // ==================== 辅助方法 ====================

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return null;
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    /**
     * 清理文件名
     */
    private String sanitizeFilename(String filename) {
        if (filename == null) return "unnamed_file";
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    // ==================== 内部类 ====================

    /**
     * 文件类型信息类
     */
    @Getter
    public static class FileTypeInfo {
        private final String fileName;
        private final String filePath;
        private final String mimeType;
        private final DataFileTypeEnum category;
        private final long fileSize;
        private final Metadata metadata;

        public FileTypeInfo(String fileName,
                            String filePath,
                            String mimeType,
                            DataFileTypeEnum category,
                            long fileSize,
                            Metadata metadata) {
            this.fileName = fileName;
            this.filePath = filePath;
            this.mimeType = mimeType;
            this.category = category;
            this.fileSize = fileSize;
            this.metadata = metadata;
        }

        @Override
        public String toString() {
            return String.format("FileTypeInfo{fileName='%s', mimeType='%s', category=%s, size=%d}",
                    fileName, mimeType, category, fileSize);
        }
    }

    /**
     * MultipartFile 信息类
     */
    @Getter
    public static class MultipartFileInfo {

        private final String originalFilename;
        private final long size;
        private final String detectedMimeType;
        private final DataFileTypeEnum category;
        private final String contentType;
        private final Metadata metadata;

        public MultipartFileInfo(String originalFilename,
                                 long size,
                                 String detectedMimeType,
                                 DataFileTypeEnum category,
                                 String contentType,
                                 Metadata metadata) {
            this.originalFilename = originalFilename;
            this.size = size;
            this.detectedMimeType = detectedMimeType;
            this.category = category;
            this.contentType = contentType;
            this.metadata = metadata;
        }

        /**
         * 获取扩展名
         */
        public String getExtension() {
            if (originalFilename == null || originalFilename.lastIndexOf('.') == -1) {
                return "";
            }
            return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        }

        /**
         * 获取不带扩展名的文件名
         */
        public String getFilenameWithoutExtension() {
            if (originalFilename == null) return "";
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex == -1) return originalFilename;
            return originalFilename.substring(0, dotIndex);
        }

        /**
         * 获取人类可读的文件大小
         */
        public String getHumanReadableSize() {
            if (size < 1024) return size + " B";
            int exp = (int) (Math.log(size) / Math.log(1024));
            char unit = "KMGTPE".charAt(exp - 1);
            return String.format("%.1f %sB", size / Math.pow(1024, exp), unit);
        }

        /**
         * 验证 MIME 类型是否匹配
         */
        public boolean isMimeTypeMatch() {
            if (contentType == null || detectedMimeType == null) return false;
            return contentType.equalsIgnoreCase(detectedMimeType);
        }

        @Override
        public String toString() {
            return String.format("MultipartFileInfo{filename='%s', size=%s, mimeType='%s', category=%s}",
                    originalFilename, getHumanReadableSize(), detectedMimeType, category);
        }
    }
}