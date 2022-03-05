package app.map.covid.activity

import android.app.Dialog
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

        binding.imgMarker.setImageResource(mHashMap.get("icon") as Int)
        binding.txtCenterName.text = mHashMap.get("centerName").toString()
        binding.txtFacilityName.text = mHashMap.get("facilityName").toString()
        binding.txtAddress.text = mHashMap.get("address").toString()
        binding.txtPhoneNumber.text = mHashMap.get("phoneNumber").toString()
        binding.txtUpdate.text = mHashMap.get("updatedAt").toString()

        return rootView
    }
}