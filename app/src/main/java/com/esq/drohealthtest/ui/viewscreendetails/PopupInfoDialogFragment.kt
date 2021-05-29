package com.esq.drohealthtest.ui.viewscreendetails

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.esq.drohealthtest.R
import com.esq.drohealthtest.databinding.FragmentPopupInfoDialogBinding


/**
 * A simple [Fragment] subclass.
 * Use the [PopupInfoDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopupInfoDialogFragment : DialogFragment() {
    private var _binding: FragmentPopupInfoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpListeners()
    }

    private fun setUpView() {
        binding.messageHeader.text = args.messageForDialog.messageHeader
        binding.messageHeaderSubtitle.text = args.messageForDialog.messageSubtitle
    }

    private fun setUpListeners() {

        if (args.messageForDialog.bagItem == null){
            binding.buttonViewBag.visibility = View.GONE
            binding.buttonDone.text = getString(R.string.try_again)
        }

        binding.buttonViewBag.setOnClickListener {
            //Navigate to BottomSheet
        }

        binding.buttonDone.setOnClickListener {
            findNavController().navigate(PopupInfoDialogFragmentDirections.actionPopupInfoDialogFragmentToStoreScreenFragment())
        }
    }

    private val args: PopupInfoDialogFragmentArgs by navArgs()
    val TAG = this::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = FragmentPopupInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(args: PopupInfoDialogFragmentArgs) =
            PopupInfoDialogFragment().apply {
                arguments = args.toBundle()
            }
    }

}