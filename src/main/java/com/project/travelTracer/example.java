package com.project.travelTracer;

import java.io.File;

public class example {
    public static void main(String[] args) {
        String absolutePath = new File("").getAbsolutePath()  + File.separator;

        String path = "images" + File.separator + "thumbnail" + File.separator + "thumbnail.png";

        System.out.println(absolutePath+path);


    }
}
