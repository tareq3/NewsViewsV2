/*
 * Created by Tareq Islam on 2/15/19 9:22 PM
 *
 *  Last modified 2/15/19 5:53 PM
 */

/*
 * Created by Tareq Islam on 2/15/19 5:51 PM
 *
 *  Last modified 1/20/19 2:43 AM
 */

package com.mti.newviewsv2.utility;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/***
 * Created by Tareq on 02,September,2018.
 */
@GlideModule
public class SimpleGlideModule extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
