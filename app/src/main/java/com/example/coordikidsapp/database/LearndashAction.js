import axios from 'axios'
import React from 'react'
import { useContext } from 'react/cjs/react.production.min';
import { AuthContext } from '../context/AuthContext';
import { AxiosContext } from '../context/AxiosContext';

const HOMESCHOOL_ID = 23660;
const POSTURE_ID = 18088;
const DYSPRAXIA_ID = 17610;
const MOTOR_SKILLS_ID = 12325;
const CLASSROOM_ID = 10123;
const PRESCHOOL_ID = 10062;


const LearndashAction = () => {
    const axiosContext = useContext(AxiosContext)
    const authContext = useContext(AuthContext)

    const getCourses = async () => {
        try {
            const res = await axiosContext.authAxios.get('/ldlms/v2/sfwd-courses');
            return res.data;    
        }
        catch(error) {
            console.log(error)
            return null
        }
    }

    const getLessons = async (id) => {
        try {
            const res = await axiosContext.authAxios.get(`/ldlms/v2/sfwd-lessons/${id}`);
            return res.data;    
        }
        catch(error) {
            console.log(error)
            return null
        }
    }

    const getTopics = async (id) => {
        try {
            const res = await axiosContext.authContext.get(`/ldlms/v2/sfwd-topic/${id}`);
            return res.data();    
        }
        catch(error) {
            console.log(error)
            return null;
        }
    }
}

export default LearndashAction;
