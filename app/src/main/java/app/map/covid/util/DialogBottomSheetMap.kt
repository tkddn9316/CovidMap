package app.map.covid.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.map.covid.R
import app.map.covid.databinding.DialogBottomSheetMapBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogBottomSheetMap(context: Context, hashMap: HashMap<String, Any>) : BottomSheetDialogFragment() {
    private val mContext: Context = context
    private val mHashMap: HashMap<String, Any> = hashMap
    private lateinit var binding: DialogBottomSheetMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_bottom_sheet_map, container, false)
        val rootView = binding.root

        binding.imgMarker.setImageResource(mHashMap["icon"] as Int)
        binding.txtCenterName.text = mHashMap["centerName"].toString()
        binding.txtFacilityName.text = mHashMap["facilityName"].toString()
        binding.txtAddress.text = mHashMap["address"].toString()
        binding.txtPhoneNumber.text = mHashMap["phoneNumber"].toString()
        binding.txtUpdate.text = mHashMap["updatedAt"].toString()

        return rootView
    }
}