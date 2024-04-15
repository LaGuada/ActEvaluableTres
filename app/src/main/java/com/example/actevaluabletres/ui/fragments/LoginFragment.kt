package com.example.actevaluabletres.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.actevaluabletres.R
import com.example.actevaluabletres.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombreUsuario = arguments?.getString("nombreUsuario")

        binding.botonLogin.setOnClickListener {
            val usuario = binding.editUsuario.text.toString().trim()
            val password = binding.editPass.text.toString().trim()


            if (usuario.isNotEmpty() && password.isNotEmpty()) {
                // Aquí iría el código para autenticar al usuario con el backend

                Toast.makeText(context, "Usuario autenticado", Toast.LENGTH_SHORT).show()

                val bundle = Bundle().apply {
                    putString("nombreUsuario", usuario)

                }

                findNavController().navigate(R.id.action_loginFragment_to_mainFragment, bundle)
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.botonRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
