package com.mocentre.tehui.system.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.backend.model.ImageInstance;
import com.mocentre.tehui.backend.param.ImageParam;
import com.mocentre.tehui.frontend.model.ImageFTInstance;
import com.mocentre.tehui.system.model.Image;

/**
 * 图片类转换
 * 
 * @author Created by yukaiji on 2017年1月19日
 */
public class ImageMapper {

    public static Image toImage(ImageParam imageParam) {
        Image img = new Image();
        img.setId(imageParam.getId());
        img.setLocation(imageParam.getLocation());
        img.setSeat(imageParam.getSeat());
        img.setSeatId(imageParam.getSeatId());
        img.setType(imageParam.getType());
        img.setSourceType(imageParam.getSourceType());
        img.setLinkUrl(imageParam.getLinkUrl());
        img.setSorting(imageParam.getSorting());
        return img;
    }

    public static ImageInstance toImageInstance(Image image) {
        ImageInstance imageIns = new ImageInstance();
        imageIns.setId(image.getId());
        imageIns.setLocation(image.getLocation());
        imageIns.setSeat(image.getSeat());
        imageIns.setSeatId(image.getSeatId());
        imageIns.setType(image.getType());
        imageIns.setSourceType(image.getSourceType());
        imageIns.setLinkUrl(image.getLinkUrl());
        imageIns.setSorting(image.getSorting());
        return imageIns;
    }

    public static ImageFTInstance toImageFTInstance(Image image) {
        ImageFTInstance imageFTIns = new ImageFTInstance();
        imageFTIns.setId(image.getId());
        imageFTIns.setLocation(image.getLocation());
        imageFTIns.setSeatId(image.getSeatId());
        imageFTIns.setType(image.getType());
        imageFTIns.setFromType(image.getSourceType());
        imageFTIns.setLinkUrl(image.getLinkUrl());
        imageFTIns.setSorting(image.getSorting());
        return imageFTIns;
    }

    public static List<ImageInstance> toImageInstanceList(List<Image> imageList) {
        List<ImageInstance> imageInsList = new ArrayList<ImageInstance>();
        if (imageList == null || imageList.size() < 1) {
            return imageInsList;
        }
        for (Image image : imageList) {
            imageInsList.add(toImageInstance(image));
        }
        return imageInsList;
    }

    public static List<ImageFTInstance> toImageFTInstanceList(List<Image> imageList) {
        List<ImageFTInstance> imageFTInsList = new ArrayList<ImageFTInstance>();
        if (imageList == null || imageList.size() < 1) {
            return imageFTInsList;
        }
        for (Image image : imageList) {
            imageFTInsList.add(toImageFTInstance(image));
        }
        return imageFTInsList;
    }
}
