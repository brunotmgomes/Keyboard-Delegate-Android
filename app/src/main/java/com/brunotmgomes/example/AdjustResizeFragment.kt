package com.brunotmgomes.example

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brunotmgomes.R
import com.brunotmgomes.keyboarddelegate.KeyboardDelegate
import kotlinx.android.synthetic.main.fragment_adjust_resize.*

class AdjustResizeFragment : Fragment(R.layout.fragment_adjust_resize) {

    // Remember to get instance when view is available
    private val keyboardDelegate by lazy { KeyboardDelegate.getInstance() }

    private val fab get() = button_floating_action

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keyboardDelegate.setInputModeOverride(viewLifecycleOwner, KeyboardDelegate.ADJUST_RESIZE)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_global_adjustPanFragment)
        }
    }

}