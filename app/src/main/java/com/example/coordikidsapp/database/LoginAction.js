import React, {useContext, useState} from 'react';
import {AuthContext} from '../context/AuthContext';
import * as Keychain from 'react-native-keychain';
import {AxiosContext} from '../context/AxiosContext';

const LoginAction = async (user, pass) => {
    const authContext = useContext(AuthContext);
    const {publicAxios} = useContext(AxiosContext);
    
    try {
        const res = await publicAxios.post(`/jwt-auth/v1/token`, {
            user,
            pass
        });

        const [accessToken, refreshToken] = res.data;

        authContext.setAuthState({
            accessToken,
            refreshToken,
            authenticated: true
        })

        await Keychain.setGenericPassword(
            'token',
            JSON.stringify({accessToken, refreshToken})
        )
    }
    catch(error) {
        Alert.alert("Login Failed", error.res.data.message)
    }
}

export default LoginAction;