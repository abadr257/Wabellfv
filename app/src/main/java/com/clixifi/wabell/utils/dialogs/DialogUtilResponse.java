package com.clixifi.wabell.utils.dialogs;

import java.util.ArrayList;

public interface DialogUtilResponse {
    void selectedValueSingleChoice(int position);
    void selectedValueSingleChoice(int position, String arrayType);
    void selectedMultiChoicelang(ArrayList<String> choices, ArrayList<String> postions, String arrayType);
}
