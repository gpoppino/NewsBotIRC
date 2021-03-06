/*
 * The MIT License
 *
 * Copyright 2017 Geronimo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.NewsBotIRC.feedreaders;

import com.github.NewsBotIRC.ConfReader.Input;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Geronimo
 */
public final class NewsFactory
{
    private static NewsFactory instance = null;

    Map<Input, Class> feedReaders;

    protected NewsFactory()
    {
        this.feedReaders = new HashMap<>();

        this.addFeedType(Input.RSS, RomeToolsFeed.class);
        this.addFeedType(Input.DB, DBFeed.class);
    }

    public void addFeedType(Input feedType, Class feedImplemenetation)
    {
        this.feedReaders.put(feedType, feedImplemenetation);
    }

    public NewsFeed createFeed(Input feedType, String feedURL)
    {
        Class feed = this.feedReaders.get(feedType);

        NewsFeed rFeed = null;
        try {
            rFeed = (NewsFeed)feed.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            LogManager.getLogger(NewsFactory.class).debug(ex.getMessage());
        }
        if (rFeed == null) return new EmptyFeed();
        rFeed.setURL(feedURL);
        return rFeed;
    }

    public static NewsFactory getInstance()
    {
        if (instance == null) instance = new NewsFactory();
        return instance;
    }
}
