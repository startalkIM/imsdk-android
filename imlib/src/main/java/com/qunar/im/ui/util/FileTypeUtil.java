package com.qunar.im.ui.util;

import com.qunar.im.ui.R;

/**
 * Created by Lex lex on 2018/5/30.
 */

public class FileTypeUtil {

    private static FileTypeUtil instance = new FileTypeUtil();

    String imgFile = "jpg|jpeg|png|gif|bmp";
    String wordFile = "doc|docx";
    String excelFile = "xls|xlsx";
    String pptFile = "ppt|pptx";
    String pdfFile = "pdf";
    String videoFile = "mp4|avi|mov|mpeg|wmv|3gp";
    String voiceFile = "mp3|mid|wav|rm|ape|flac|amr";
    String zipFile = "zip|rar|7z|jar";
    String txtFile = "txt|java";
    String apkFile = "apk";
    String htmlFile = "html";

    public static FileTypeUtil getInstance(){
        return instance;
    }

    public int getFileTypeBySuffix(String fExt) {
        int fileType = R.string.atom_ui_ic_unknow;

        if (imgFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_image;
        } else if (wordFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_word;
        } else if (excelFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_excel;
        } else if (pptFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_ppt;
        } else if (videoFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_video;
        } else if (voiceFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_video;
        } else if (zipFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_zip;
        } else if (pdfFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_pdf;
        } else if (htmlFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_html;
        } else if (txtFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_txt;
        } else if (apkFile.contains(fExt)) {
            fileType = R.string.atom_ui_ic_apk;
        }
        return fileType;
    }
}
