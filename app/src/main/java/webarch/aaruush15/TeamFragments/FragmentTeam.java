package webarch.aaruush15.TeamFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import webarch.aaruush15.R;
import webarch.aaruush15.ViewFlipperLibraryFiles.BaseFlipAdapter;
import webarch.aaruush15.ViewFlipperLibraryFiles.FlipSettings;

/**
 * @author Yalantis
 */
public class FragmentTeam extends Fragment {

    final Context context = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootview=inflater.inflate(R.layout.activity_friends, container, false);
        final ListView patrons = (ListView) rootview.findViewById(R.id.patrons);
        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
        patrons.setAdapter(new MemberModelAdapter(getActivity(), Utils.patrons, settings));
        patrons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String contact = (Utils.patrons.get(i).getInterests().get(2));
                if (contact.contains("@")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contact});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                    startActivity(Intent.createChooser(intent, ""));
                } else {
                    String x = "tel:" + contact;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse(x));
                    startActivity(callIntent);
                }
                //Toast.makeText(getActivity(),"Clicked on "+(Utils.patrons.get(i).getNickname()),Toast.LENGTH_SHORT).show();
            }
        });
        return rootview;
    }

    class MemberModelAdapter extends BaseFlipAdapter<MemberModel>
    {
        private final int PAGES = 3;
        private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2};
        public MemberModelAdapter(Context context, List<MemberModel> items, FlipSettings settings)
        {
            super(context, items, settings);
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, MemberModel model1, MemberModel model2)
        {
            final MembersHolder holder;
            if (convertView == null)
            {
                holder = new MembersHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.team_item_normal, parent, false);
                holder.leftName=(TextView) convertView.findViewById(R.id.post1);
                holder.RightName=(TextView) convertView.findViewById(R.id.post2);
                holder.leftAvatar = (ImageView) convertView.findViewById(R.id.first);
                holder.rightAvatar = (ImageView) convertView.findViewById(R.id.second);
                holder.infoPage = getActivity().getLayoutInflater().inflate(R.layout.team_item_flipped, parent, false);
                holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);
                for (int id : IDS_INTEREST)
                    holder.interests.add((TextView) holder.infoPage.findViewById(id));
                convertView.setTag(holder);
            }
            else
            {
                holder = (MembersHolder) convertView.getTag();
            }

            switch (position)
            {
                case 1:
                    holder.leftAvatar.setImageResource(model1.getAvatar());
                    holder.leftName.setText(model1.getInterests().get(0));
                    if (model2 != null) {
                        holder.rightAvatar.setImageResource(model2.getAvatar());
                        holder.RightName.setText(model2.getInterests().get(0));
                    }
                    break;
                default:
                    fillHolder(holder, position == 0 ? model1 : model2);
                    holder.infoPage.setTag(holder);
                    return holder.infoPage;
            }
            return convertView;
        }

        @Override
        public int getPagesCount()
        {
            return PAGES;
        }

        private void fillHolder(MembersHolder holder, MemberModel model)
        {
            if (model == null)
                return;
            Iterator<TextView> iViews = holder.interests.iterator();
            Iterator<String> iInterests = model.getInterests().iterator();
            while (iViews.hasNext() && iInterests.hasNext())
                iViews.next().setText(iInterests.next());
            holder.infoPage.setBackgroundColor(getResources().getColor(model.getBackground()));
            holder.nickName.setText(model.getNickname());
        }

        class MembersHolder
        {
            ImageView leftAvatar;
            ImageView rightAvatar;
            TextView leftName;
            TextView RightName;
            View infoPage;
            List<TextView> interests = new ArrayList<>();
            TextView nickName;
        }
    }
}