import Vue from 'vue'
import Vuex from 'vuex'
export default{
    state:{
        //电影id
        fid:0,

    },
    mutations:{
        setFid(state,fid){
            state.fid = fid
            localStorage.setItem('fid',fid)
        }
    },
    actions:{

    }
    
}