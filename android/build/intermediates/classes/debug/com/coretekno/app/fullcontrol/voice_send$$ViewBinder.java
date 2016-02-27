// Generated code from Butter Knife. Do not modify!
package com.coretekno.app.fullcontrol;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class voice_send$$ViewBinder<T extends com.coretekno.app.fullcontrol.voice_send> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findOptionalView(source, 2131558568, null);
    target.pc_name = finder.castView(view, 2131558568, "field 'pc_name'");
    view = finder.findRequiredView(source, 2131558642, "field 'user_name'");
    target.user_name = finder.castView(view, 2131558642, "field 'user_name'");
    view = finder.findRequiredView(source, 2131558643, "field 'user_connect_id'");
    target.user_connect_id = finder.castView(view, 2131558643, "field 'user_connect_id'");
    view = finder.findRequiredView(source, 2131558540, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558540, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558539, "field 'drawer'");
    target.drawer = finder.castView(view, 2131558539, "field 'drawer'");
    view = finder.findRequiredView(source, 2131558541, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131558541, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131558565, "field 'fab_voice_send' and method 'voice_send_click'");
    target.fab_voice_send = finder.castView(view, 2131558565, "field 'fab_voice_send'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.voice_send_click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558623, "field 'record_time_text'");
    target.record_time_text = finder.castView(view, 2131558623, "field 'record_time_text'");
    view = finder.findRequiredView(source, 2131558624, "field 'record_text'");
    target.record_text = finder.castView(view, 2131558624, "field 'record_text'");
  }

  @Override public void unbind(T target) {
    target.pc_name = null;
    target.user_name = null;
    target.user_connect_id = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
    target.fab_voice_send = null;
    target.record_time_text = null;
    target.record_text = null;
  }
}
