package com.example.open.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.open.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.open.utils.Utils.dp2px;
import static com.example.open.utils.Utils.getTextView;

/**
 * 用户页面
 *  布局：LinearLayout（LL）
 *  组件：TextView(tv) ImageView(iv) CircleImageView(civ) ImageButton(ib)
 *  上(RL)：背景图(iv)  设置(ib-左上)     签到(ib-右上)
 *                      等级头像(iv-中1)+【b】等级数字与【c】头衔(tv-中1)
 *                          圆形头像(civ-中2)
 *                          tv[性别(drawableLeft)+【a】昵称（String）]
 *  中(LL-v)： LL:[tv[coins图(drawableLeft)+【d】财富值(String)]+ 分隔符+tv[用户图标(drawableLeft)+【e】账号(String)]]
 *                           【f】个人说明（tv）
 *  下(LL-h)：功能按钮*6(在主xml页面中留出此区域，定义方法动态生成，功能名字的集合存放在String）
 *  重写onCreateView()生成以上三块布局，设置上和中的TextView（abcdef）,图标部分在xml里定义，再运行生成下方布局的方法
 */
public class UserFragment extends Fragment {
    private LinearLayout mContainer;
    /**
     * 预：准备好fragment_user.xml
     * 1.绑定xml页面与要生成的View
     * 2.设置xml里用户信息的TextView
     * 3.动态生成下方区域的功能按钮*6
     * 4.返回View
     */
    public static UserFragment newInstance(int num) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        fragment.setArguments(args);
        return fragment;
    }
    @Override @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View v = inflater.inflate(R.layout.fragment_user,container,false);

        TextView nickname = (TextView)v.findViewById(R.id.name_text);
                 nickname.setText("a");
        TextView lever_num = (TextView)v.findViewById(R.id.level_num);
                 lever_num.setText("Lv.1");
        TextView lever_name = (TextView)v.findViewById(R.id.level_name);
                 lever_name.setText("000");
        TextView coin_num = (TextView)v.findViewById(R.id.coins);
                 coin_num.setText("000000");
        TextView user_id = (TextView)v.findViewById(R.id.user_id);
                 user_id.setText("111111");
        TextView user_words = (TextView)v.findViewById(R.id.user_words);
                 user_words.setText("该用户没有说什么");
        mContainer =(LinearLayout)v.findViewById(R.id.info_container);
        setContainer();
      return  v;
    }

    private void setContainer() {
        //图标与名
        int[] icons = {
                R.drawable.ic_cup,
                R.drawable.ic_friends,
                R.drawable.ic_heart,
                R.drawable.ic_like,
                R.drawable.ic_reward,
                R.drawable.ic_works
         };
        String[] infos =getResources().getStringArray(R.array.user_infos);

        //
        Context context = getContext();
        //添加放置图标的格子
        //两行
        for (int i = 0;i<2;i++){
      // 先设置lp*1,再设置ll*3,再添加到ll，lp到mCon~
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            LinearLayout line = new LinearLayout(getContext());
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setWeightSum(4);//4列，一行4个格子
            mContainer.addView(line,lp);

            if (i==0){
                //在第一行与第二行之间，设置横着的分割线
                View divider = new View(context);
                //分割线的颜色
                divider.setBackgroundColor(0xFFEEEEEE);
                //分割线的宽高 高为0→ ─
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(MATCH_PARENT,1);
                //设置边距
                lp1.setMargins(dp2px(context,27 ), dp2px(context,10 ), dp2px(context,27 ), dp2px(context,10 ));
                //加到下方的LinearLayout
                mContainer.addView(divider, lp1);
            }
            //往格子里添加图标和标题
            //判断是第一行还是第二行，1行的4个格子，2行的两个格子
            int num = (i == 0?4:2);
            //根据行数添加对应数目的标题
            for (int j = 0;j < num;j++){
                //格子宽高
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0,WRAP_CONTENT);
                lp2.weight = 1;
                //设置索引
                int idx = i * 4 + j;
                //添加对应的标题
                TextView v =getTextView(context,infos[idx],R.color.text_color,12.F);
                v.setBackgroundResource(R.drawable.highlight_bkg);
                v.setGravity(Gravity.CENTER_HORIZONTAL);
                //添加图标在组件的顶部
                v.setCompoundDrawablesRelativeWithIntrinsicBounds(0,icons[idx],0,0);
                v.setCompoundDrawablePadding(dp2px(context,8));
                //文字和图标和对应的格子lp2都加到LinearLayout里
                line.addView(v,lp2);
                //一行的三个|分割线，2行的两个|分割线,关注和粉丝之间并没有线
               int num1 = (i == 0 ? 3 : 2);
               if (j != num1){
                    LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(1,MATCH_PARENT);
                    View split = new View(context);
                    split.setBackgroundColor(0xFFEEEEEE);
                    line.addView(split,lp3);
               }
            }
        }


    }


}
