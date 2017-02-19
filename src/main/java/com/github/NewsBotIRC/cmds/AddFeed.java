/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.NewsBotIRC.cmds;

import com.github.NewsBotIRC.IRCMediator;
import java.net.MalformedURLException;

/**
 *
 * @author Geronimo
 */
public class AddFeed implements Cmd
{

    @Override
    public String get()
    {
        return "add";
    }

    @Override
    public void action(IRCMediator m, String params)
    {
        if (!params.startsWith("http")) {
            m.showMessage("Invalid feed");
            return;
        }

        try {
            m.addFeed(params);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            m.showMessage("Invalid feed");
        }
    }

}
