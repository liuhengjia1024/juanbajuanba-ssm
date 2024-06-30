package com.liuhengjia.common;

public class PathConstant {
    public static final String AVATAR = "avatar";
    public static final String COURSE = "course";
    public static final String STUDENT_AVATAR_DIR_PATH = "/img/avatar/student/";
    public static final String[] STUDENT_AVATAR_PATH_DIRS = new String[]{"img", AVATAR, "student"};
    public static final String INSTRUCTOR_AVATAR_DIR_PATH = "/img/avatar/instructor/";
    public static final String[] INSTRUCTOR_AVATAR_PATH_DIRS = new String[]{"img", AVATAR, "instructor"};
    public static final String ADMINISTRATOR_AVATAR_DIR_PATH = "/img/avatar/administrator/";
    public static final String[] ADMINISTRATOR_AVATAR_PATH_DIRS = new String[]{"img", AVATAR, "administrator"};
    public static final String CATEGORY_IMG_DIR_PATH = "/img/category/";
    public static final String[] CATEGORY_IMG_PATH_DIRS = new String[]{"img", "category"};
    public static final String COURSE_IMG_DIR_PATH = "/img/course/";
    public static final String[] COURSE_IMG_PATH_DIRS = new String[]{"img", COURSE};

    private PathConstant() {
    }

    public static String getVideoImgDirPath(Integer courseId) {
        return "/video/course" + courseId + "/";
    }

    public static String[] getVideoImgPathDirs(Integer courseId) {
        return new String[]{"video", COURSE + courseId};
    }

    public static String getMaterialImgDirPath(Integer courseId) {
        return "/material/course" + courseId + "/";
    }

    public static String[] getMaterialImgPathDirs(Integer courseId) {
        return new String[]{"material", COURSE + courseId};
    }
}
