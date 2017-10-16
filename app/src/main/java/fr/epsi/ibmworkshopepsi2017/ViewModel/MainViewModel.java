package fr.epsi.ibmworkshopepsi2017.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.hardware.input.InputManager;

import fr.epsi.ibmworkshopepsi2017.Models.Delivery;

/**
 * Created by HBxAa on 16/10/2017.
 */

public class MainViewModel extends ViewModel {

    private static MainViewModel mainViewModel;

    private Delivery delivery;
    public MainViewModel()
    {
        getInstance();
    }

    public void  SetDelivery(Delivery delivery)
    {
       delivery = this.delivery;
    }

    public static MainViewModel getInstance(){

        if(mainViewModel == null)
        {
            mainViewModel = new MainViewModel();
        }
        return mainViewModel;
    }

    public Delivery GetDelivery()
    {
        return this.delivery;
    }


}
