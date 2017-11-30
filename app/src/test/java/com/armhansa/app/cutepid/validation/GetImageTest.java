package com.armhansa.app.cutepid.validation;

import android.app.Activity;
import android.net.Uri;

import com.armhansa.app.cutepid.tool.GetPostImage;

import org.junit.Test;

/**
 * Created by student on 30/11/2017 AD.
 */

public class GetImageTest extends Activity {

    @Test
    public void getImageTest() {
        GetPostImage.getImage(this);
    }

}
