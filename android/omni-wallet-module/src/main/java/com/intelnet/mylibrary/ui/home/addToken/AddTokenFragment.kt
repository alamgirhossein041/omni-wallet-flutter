package com.intelnet.mylibrary.ui.home.addToken

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.intelnet.mylibrary.base.BaseFragment
import com.intelnet.mylibrary.databinding.FragmentAddTokenBinding
import com.intelnet.mylibrary.ui.home.HomeFragmentDirections
import com.intelnet.mylibrary.ui.home.HomeViewModel
import com.intelnet.mylibrary.ui.home.network.NetDialogFragment
import com.intelnet.mylibrary.util.Status
import com.intelnet.mylibrary.util.setNavigationResult
import dagger.hilt.android.AndroidEntryPoint
import org.web3j.crypto.WalletUtils


@AndroidEntryPoint
class AddTokenFragment : BaseFragment<FragmentAddTokenBinding, HomeViewModel>() {

    //    override val viewModel: AddTokenViewModel by viewModels()
    override val viewModel: HomeViewModel by activityViewModels()

    private var contractAddressTemp: String = ""

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddTokenBinding.inflate(inflater, container, false)

    override fun initControl() {
        binding.imgBack.setOnClickListener {
            binding.edtTokenAddress.onFocusChangeListener = null
            backToPrevious()
        }

        binding.btnImportToken.setOnClickListener {
            val addressInput = binding.edtTokenAddress.text.toString().trim()
            checkAndCall(addressInput)
        }

        binding.viewClickImportToken.isEnabled = !viewModel.setting.disableNetwork
        binding.viewClickImportToken.setOnClickListener {
            NetDialogFragment.newInstance(
                fManager,
                viewModel.lstItemNetwork,
                chooseNetworkListener = {
                    if(it==-1)
                        return@newInstance
                    setNavigationResult("network_change", it)
                    viewModel.setDefaultNetworkInfo(it)
                    setUiDefaultNetWork()
                },
                addNetworkListener = {
                    navigate(
                        HomeFragmentDirections.actionHomeFragmentToAddNetworkFragment()
                    )
                }
            )
        }

        binding.edtTokenAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0==null)
                    return
                if(p0.isNotBlank())
                    checkAndCall(p0.toString().trim())
            }


        })

    }

    override fun initUI() {
        setUiDefaultNetWork()
    }

    private fun setUiDefaultNetWork() {
        val itemNetwork = viewModel.getItemNetworkDefault()
        itemNetwork?.let {
            binding.txtDot.setBackgroundColor(it.color)
            binding.txtNet.text = it.name
        }
    }

    private fun updateUI(symbol: String, decimal: String) {
        binding.edtTokenSymbol.setText(symbol)
        binding.edtTokenDecimal.setText(decimal)
    }

    override fun initEvent() {
        viewModel.tokenLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                when (data.responseType) {
                    Status.LOADING -> {
                        showLoadingDialog()
                        binding.btnImportToken.isEnabled = false
                    }
                    Status.SUCCESSFUL -> {
                        hideDialog()
                        binding.btnImportToken.isEnabled = true
                        data.data?.let { map ->
                            setNavigationResult("network_change", 0)
                            contractAddressTemp = map["contractAddress"] ?: ""
                            updateUI(map["symbol"] ?: "", map["decimals"] ?: "")
                        }
                    }
                    Status.ERROR -> {
                        hideDialog()
                        binding.btnImportToken.isEnabled = false
                        data.error?.message?.let { key ->
                            if (key == "token_address_existed")
                                showToast("?????a ch??? token ???? t???n t???i!")
                            else
                                showToast("Vui l??ng nh???p ????ng ?????a ch??? token!")
                        }
                    }
                }
            }
        }
    }

    private fun checkAndCall(contractAddress:String) {
        if (contractAddress.isBlank())
            return
        if (WalletUtils.isValidAddress(contractAddress))
            if (contractAddressTemp != contractAddress)
                viewModel.loadInforToken(contractAddress)
            else
                importTokenSuccess()
        else {
            showToast("Vui l??ng nh???p ????ng ?????a ch??? token!")
        }

    }

    private fun importTokenSuccess() {
        showToast("Th??m token th??nh c??ng!")
        viewModel.addContractAddressToPref(contractAddressTemp)
        backToPrevious()
    }

    override fun initConfig() {

    }


}