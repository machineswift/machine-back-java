package com.machine.starter.redis.constant;

/**
 * Redis Key 的前缀
 */
public class RedisPrefix4DataConstant {

    public static class Area{
        /**
         * 区域树 key
         */
        public static final String DATA_AREA_TREE_KEY = "data:area:tree:key";

        /**
         * 区域树 数据
         */
        public static final String DATA_AREA_TREE_DATA = "data:area:tree:data:";
    }

    public static class Material {

        /**
         * 素材地址
         */
        public static final String DATA_MATERIAL_URL_KEY = "data:material:url:key:";

        /**
         * 素材缩略图地址
         */
        public static final String DATA_MATERIAL_THUMBNAIL_URL_KEY = "data:material:thumbnailUrl:key:";
    }

    public static class MaterialCategory {

        /**
         * 素材分类 key
         */
        public static final String DATA_MATERIAL_CATEGORY_TREE_KEY = "data:materialCategory:tree:key";

        /**
         * 素材分类 数据
         */
        public static final String DATA_MATERIAL_CATEGORY_TREE_DATA = "data:materialCategory:tree:data:";
    }


    public static class Attachment {

        /**
         * 附件地址
         */
        public static final String DATA_ATTACHMENT_URL_KEY = "data:attachment:url:key:";

        /**
         * 附件缩略图地址
         */
        public static final String DATA_ATTACHMENT_THUMBNAIL_URL_KEY = "data:attachment:thumbnailUrl:key:";
    }


    public static class AttachmentCategory {

        /**
         * 附件分类 key
         */
        public static final String DATA_ATTACHMENT_CATEGORY_TREE_KEY = "data:attachmentCategory:tree:key";

        /**
         * 附件分类 数据
         */
        public static final String DATA_ATTACHMENT_CATEGORY_TREE_DATA = "data:attachmentCategory:tree:data:";
    }


    public static class TagCategory {

        /**
         * 智能标签分类 key
         */
        public static final String DATA_TAG_CATEGORY_TREE_KEY = "data:tagCategory:tree:key:";

        /**
         * 智能标签分类 数据
         */
        public static final String DATA_TAG_CATEGORY_TREE_DATA = "data:tagCategory:tree:data:";
    }
}
