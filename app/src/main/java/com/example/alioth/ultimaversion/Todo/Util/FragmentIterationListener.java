package com.example.alioth.ultimaversion.Todo.Util;

import android.net.Uri;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by Alioth on 19/06/2015.
 */
public interface FragmentIterationListener extends Serializable {
    public void onFragmentIteration(int idFragment, Object ... dato);
}
