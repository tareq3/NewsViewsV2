/*
 * Created by Tareq Islam on 2/16/19 1:12 PM
 *
 *  Last modified 2/16/19 12:48 AM
 */

/*
 * Created by Tareq Islam on 2/16/19 12:47 AM
 *
 *  Last modified 2/16/19 12:47 AM
 */

package com.mti.newviewsv2.utility;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.mti.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
