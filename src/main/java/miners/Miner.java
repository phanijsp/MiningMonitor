package miners;

import models.MiningAlgorithm;
import org.json.JSONArray;
import org.json.JSONObject;

interface MinerInterface{

    boolean isInstanceOf(String res) throws Exception;
    double getTotalHashRate(String res);
    MiningAlgorithm[] getMiningAlgorithms(String res);

}
public enum Miner implements MinerInterface{

    T_REX {
        @Override
        public boolean isInstanceOf(String res) {
            return false;
        }

        @Override
        public double getTotalHashRate(String res) {
            return 0;
        }

        @Override
        public MiningAlgorithm[] getMiningAlgorithms(String res) {
            return new MiningAlgorithm[0];
        }
    },

    LOL_MINER{
        @Override
        public boolean isInstanceOf(String res) throws Exception{
            if( res!=null && res.length() >0) {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getString("Software").contains("lolMiner")) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public double getTotalHashRate(String res) {
            return 0;
        }

        @Override
        public MiningAlgorithm[] getMiningAlgorithms(String res) {
            if( res!=null && res.length() >0) {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getString("Software").contains("lolMiner")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("Algorithms");
                    MiningAlgorithm[] miningAlgorithms = new MiningAlgorithm[jsonArray.length()];
                    for(int i = 0 ; i < miningAlgorithms.length ; i ++){
                        miningAlgorithms[i] = new MiningAlgorithm(
                                jsonArray.getJSONObject(i).getString("Algorithm"),
                                jsonArray.getJSONObject(i).getDouble("Total_Performance"),
                                jsonArray.getJSONObject(i).getString("Performance_Unit")
                        );
                    }
                    return miningAlgorithms;
                }
            }
            return new MiningAlgorithm[0];
        }
    }
}
