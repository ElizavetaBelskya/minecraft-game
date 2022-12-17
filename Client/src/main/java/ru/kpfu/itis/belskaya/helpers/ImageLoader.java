package ru.kpfu.itis.belskaya.helpers;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ImageLoader {



    public static Image loadImage(int typeId) throws ResourceLoadingException {
        try {
            Image img = ImageIO.read(new File(getImageResource(typeId)));
            return img;
        } catch (IOException e) {
            throw new ResourceLoadingException(e);
        }
    }

    public static String getImageResource(int id) throws ResourceLoadingException {
        String resource;
        switch (id) {
            case 0:
                resource = "stiv.png";
                break;
            case 1:
                resource = "amethyst.png";
                break;
            case 2:
                resource = "brick.png";
                break;
            case 3:
                resource = "dimond.png";
                break;
            case 4:
                resource = "dirt.png";
                break;
            case 5:
                resource = "lava.png";
                break;
            case 6:
                resource = "redBrick.png";
                break;
            case 7:
                resource = "stone.jpg";
                break;
            case 8:
                resource = "tnt.png";
                break;
            default:
                resource = "dirt.png";
        }
        String resLink;
        try {
            resLink = URLDecoder.decode(ImageLoader.class.getClassLoader().getResource(resource).getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ResourceLoadingException("Unable to find or download the resource", e);
        }
        return resLink;
    }


}
