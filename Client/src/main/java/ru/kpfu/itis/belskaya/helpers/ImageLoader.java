package ru.kpfu.itis.belskaya.helpers;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ImageLoader {

    public static Image loadImage(String fileLink) throws ResourceLoadingException {
        try {
            Image img = ImageIO.read(new File(getImageResource(fileLink)));
            return img;
        } catch (IOException e) {
            throw new ResourceLoadingException(e);
        }
    }

    public static String getImageResource(String resource) throws ResourceLoadingException {
        String resLink;
        try {
            resLink = URLDecoder.decode(ImageLoader.class.getClassLoader().getResource(resource).getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ResourceLoadingException("Unable to find or download the resource", e);
        }
        return resLink;
    }


}
