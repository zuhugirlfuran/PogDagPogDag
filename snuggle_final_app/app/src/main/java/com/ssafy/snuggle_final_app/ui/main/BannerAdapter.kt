import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val fragmentList: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    // 배너 추가
    fun addBannerFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
    }


    // 배너 여러 개 추가
    fun addBannerFragments(fragments: List<Fragment>) {
        val startIndex = fragmentList.size
        fragmentList.addAll(fragments)
        notifyItemRangeInserted(startIndex, fragments.size)
    }

    // 배너 제거
    fun removeBannerFragment(position: Int) {
        if (position in 0 until fragmentList.size) {
            fragmentList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // 배너 리스트 업데이트
    fun setBannerFragments(fragments: List<Fragment>) {
        fragmentList.clear()
        fragmentList.addAll(fragments)
        notifyDataSetChanged()
    }
}
