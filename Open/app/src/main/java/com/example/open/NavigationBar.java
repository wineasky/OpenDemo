package com.example.open;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 通过代码动态绘制导航栏
 * 1.继承LineatLayout，实现onClickListener
 * 2.定义成员变量：TAB_TAG、IMAGE_TAG、listener→创建OnTabSelectListener接口、current
 * 3.方法：a.构造方法*3
 *         b.构造方法里的方法：init(){getItemView()、selectAt(){ tag()}}
 *         c.重写的方法：onClick(){onTabSelected()、selectAt()}
 */
public class NavigationBar extends LinearLayout implements View.OnClickListener{
    //点击的区域和图标的tag，%d，不设置TAB_TAG点击没法切换
    private static final String TAB_TAG = "tab_%d";
    private static final String IMAGE_TAG = "image_%d";

    //OnTabSelector接口，用于接收导航栏被选中的索引
    private OnTabSelectListener listener;
    // 当前所在索引？
    private int current = -1;

    //LinearLayout里的构造，一共有四个，虽然不知道为什么要写这三个，默认的指向的super
    public NavigationBar(Context context) {
       this(context,null);
    }
    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();//初始化↓
    }


    /** init()
     * 导航栏初始化：
     *       1.设置要展示的图标、背景
     *       2.设置LinearLayout参数:排列方式、权重、图标的摆放
     *       3.把图标添加到对应的格子
     *       4.设置默认的索引位置
     * */
    //【NavigationBar(c,a,d)】
    private void init(){
        //图标资源 未选中的是灰色
        int[] icons = {R.drawable.ic_tab_home,
                R.drawable.ic_tab_rank,
                R.drawable.ic_tab_circle_focus,
                R.drawable.ic_tab_album,
                R.drawable.ic_tab_user};
        //设置背景色
        setBackgroundColor(Color.WHITE);
        //从图标组中获取图标个数
        int len = icons.length;
        //同xml里的LinearLayout，设置水平排列
        setOrientation(HORIZONTAL);
        //通过图标个数平均分配位置，同layout_weight
        setWeightSum(len);
        //设置LinearLayout参数，宽：0，高：自适应
        LayoutParams lp = new LayoutParams(0, MATCH_PARENT);
        //设置比例，因为weight为float类型，所以用f
        lp.weight = 1.f;
        for (int i = 0;i < len; i++) {
            //通过自定义的方法获取RelativeLayout，并在相对布局里放置图标
            RelativeLayout itemView = getItemView(i, icons[i]);
            //写完后面的方法忘了添加addView，造成导航栏不显示
            // 循环添加图标到RelativeLayout
            addView(itemView, lp);
        }
        //默认呆在0索引的位置
        selectAt(0);
    }
    private RelativeLayout getItemView(int i, int iconId) {
        //getContext()是View自带的，传入构造，获取布局
        RelativeLayout layout = new RelativeLayout(getContext());
        //设置点击事件
        layout.setOnClickListener(this);
        //设置Tag，相当于id,因为TAB_TAG="tab_%d",%d会被格式化成i的值
        layout.setTag(String.format(TAB_TAG,i));
        //相当于xml里的高和宽设置成wrap_content
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        //图标所在的RelativeLayout居中在LinearLayout
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        //用ImageView的构造来获取ImageView
        ImageView imageView = new ImageView(getContext());
        //设置imageView的图片为iconId对应的文件
        imageView.setImageResource(iconId);
        //同TAB_TAG标题标签的格式化
        imageView.setTag(String.format(IMAGE_TAG,i));
        //把imageView添加到RelativeLayout，lp为固定位置
        layout.addView(imageView,lp);
        //返回的RelativeLayout会显示水平排列的5个图标
        return layout;
    }
    //【init】【onClick】获取选中的导航索引
    private  void selectAt(int index){
        /*通过tag()↓来获取选中的图标的tag，
         用findViewWithTag()绑定tag,
        setSelected设置是否选中，如果-1就选中
        以此来控制图标是暗还是亮*/
        if (current != -1) {//当前的位置不为-1，不选中
            findViewWithTag(tag(current)).setSelected(false);

        }//-1，选中
        findViewWithTag(tag(index)).setSelected(true);
        /*让index位置的图标回到置顶的位置，
        此处少加了造成图标点击之后一直亮着。。
        但是虽然设置了，却点一下就熄灭，不能把current =index;加到else里*/
        current =index;
    }
    //【selectAt】获取图片的标签
    private String tag(int i){
        return String.format(IMAGE_TAG,i);
    }

    /**onClick()
     *点击标签而发生的事件：
     *      1.判断tag是否定义，是就进行下一步
     *      2.通过tag获取到相应的索引数字
     *      3.判断图标是否被点击，以此来控制当前索引，来点亮图标
     */
    @Override
    public void onClick(View v) {
        //点击的标签未定义时，退出方法
        if (null == v.getTag())
            return;
        //把获取到的tag对象，强转为字符串
        String tag = (String)v.getTag();
        /*因为tag字符串的格式为xx_%d,
      用正则分割成{xx[]，%d[]},[1]取到%d[]，再用Integer转字符串为数字
         */
        int idx = Integer.parseInt(tag.split("_")[1]);
        /*listener判断图标是否被选中，
        如果触发了选中图标的事件,
        就把已经获取到的索引设置成选中
         */
        if (null != listener)
            listener.onTabSelected(idx);
             selectAt(idx);
    }
    //【MainActivity】设置监听
    public void setOnTabSelectListener(OnTabSelectListener listener){
        this.listener = listener;
    }


}
