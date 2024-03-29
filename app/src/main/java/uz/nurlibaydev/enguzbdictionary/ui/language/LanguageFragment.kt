package uz.nurlibaydev.enguzbdictionary.ui.language

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import timber.log.Timber
import uz.nurlibaydev.enguzbdictionary.BuildConfig
import uz.nurlibaydev.enguzbdictionary.R
import uz.nurlibaydev.enguzbdictionary.databinding.FragmentLanguageBinding

/**
 *  Created by Nurlibay Koshkinbaev on 05/08/2022 14:15
 */

class LanguageFragment : Fragment(R.layout.fragment_language) {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLanguageBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.btnEngUzb.setOnClickListener {
            try {
                navController.navigate(LanguageFragmentDirections.actionLanguageFragmentToMainFragment())
            } catch (e: Exception) { }
        }

        binding.btnUzbEng.setOnClickListener {
            try {
                navController.navigate(LanguageFragmentDirections.actionLanguageFragmentToUzMainFragment())
            } catch (e: Exception) { }
        }

        binding.ivMenu.setOnClickListener {
            val menu = PopupMenu(requireContext(), it)
            menu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.intentEmail -> {
                        sendMail()
                        true
                    }
                    R.id.rate -> {
                        rateApp()
                        true
                    }
                    R.id.share_app -> {
                        shareApp()
                        true
                    }
                    else -> false
                }
            }

            menu.inflate(R.menu.main_menu)

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(menu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Timber.tag("Main").e(e, "Error showing menu icons.")
            } finally {
                menu.show()
            }
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
            var shareMessage = "English-Uzbek Dictionary".trim() + "\n"
            shareMessage = "${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Baham ko'rmoq"))
        } catch (e: java.lang.Exception) {
            e.toString()
        }
    }

    private fun rateApp() {
        val uri: Uri = Uri.parse("market://details?id=uz.unidev.dictionary")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=uz.unidev.dictionary")
                )
            )
        }
    }

    private fun sendMail() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "koshkinbayevn@gmail.com", null))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(intent, "Choose an Email client :"))
    }
}