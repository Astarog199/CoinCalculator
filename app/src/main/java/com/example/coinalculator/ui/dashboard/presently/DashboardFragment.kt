package com.example.coinalculator.ui.dashboard.presently

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.databinding.FragmentDashboardBinding
import com.example.coinalculator.ui.dashboard.data.CoinModel
import com.example.coinalculator.ui.dashboard.data.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCoin()
        binding.button.setOnClickListener {
            getCoin()
        }
    }

    fun getCoin(){
        RetrofitInstance.searchCoinApi.getCoinApi("bitcoin", "usd")
            .enqueue(object : Callback<CoinModel> {
                override fun onResponse(
                    call: Call<CoinModel>,
                    response: Response<CoinModel>
                ) {
                    if (response.isSuccessful){
                        val result = response.body() ?: return
                        binding.coin.text = result.coin.usd.toString()
                    }
                }
                override fun onFailure(call: Call<CoinModel>, t: Throwable) {
                    Log.e("Network", "Something went wrong", t)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}