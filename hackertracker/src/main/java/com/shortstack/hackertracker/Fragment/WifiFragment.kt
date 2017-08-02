package com.shortstack.hackertracker.Fragment

import android.app.Activity
import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiEnterpriseConfig
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.security.KeyChain
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.orhanobut.logger.Logger
import com.shortstack.hackertracker.Application.App
import com.shortstack.hackertracker.R
import kotlinx.android.synthetic.main.fragment_wifi.*

class WifiFragment : Fragment() {

    private val INSTALL_KEYSTORE_CODE = 1001

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_wifi, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        install.setOnClickListener { promptInstallCert() }
        connect.setOnClickListener { connectWifi() }
    }


    private fun connectWifi() {
        val wifi = getWifiManager()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val wifiConfig = WifiConfiguration()
            wifiConfig.SSID = "DefCon"
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP)
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X)

            val enterpriseConfig = WifiEnterpriseConfig()

            enterpriseConfig.identity = "defcon"
            enterpriseConfig.password = "defcon"
            enterpriseConfig.eapMethod = WifiEnterpriseConfig.Eap.PEAP
            enterpriseConfig.phase2Method = WifiEnterpriseConfig.Phase2.MSCHAPV2
            //enterpriseConfig.caCertificate =

            wifiConfig.enterpriseConfig = enterpriseConfig

            Logger.d("Status: " + wifiConfig.status)

            val network = wifi.addNetwork(wifiConfig)
            wifi.enableNetwork(network, true)
        }
    }


    private fun promptInstallCert() {
        val stream = context.assets.open("digicertca.cer")
        if (stream != null) {
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()

            Logger.d("Have obtained the  buffer.")

            val installIntent = KeyChain.createInstallIntent()
            installIntent.putExtra(KeyChain.EXTRA_CERTIFICATE, buffer)
            installIntent.putExtra(KeyChain.EXTRA_NAME, "Digicert DefCon CA")
            // TODO: If possible, pre-select 'WIFI' instead of 'VPN/Apps'.

            (context as Activity).startActivityForResult(installIntent, INSTALL_KEYSTORE_CODE)
        }
    }

    private fun getWifiManager() = App.application.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    companion object {
        fun newInstance(): WifiFragment {
            return WifiFragment()
        }
    }
}