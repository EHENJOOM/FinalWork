package com.zhk.panel.teacher.check;

import com.zhk.login.LoginBean;
import com.zhk.panel.student.subject.MatchBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:43
 * @description 确认学生选课题面板
 */
public class CheckSubjectPanel extends JPanel implements CheckSubjectView {

    private CheckSubjectAdapter adapter;
    private JTable table;

    private List<MatchBean> matchBeans;
    private CheckSubjectPresenter presenter = new CheckSubjectPresenter();

    public CheckSubjectPanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new CheckSubjectAdapter();
        table = new JTable(adapter);
        scrollPane.setViewportView(table);

        table.getColumnModel().getColumn(12).setCellRenderer(new CheckSubjectRender("接收"));
        table.getColumnModel().getColumn(12).setCellEditor(new CheckSubjectEditor("接收"));
        table.getColumnModel().getColumn(13).setCellRenderer(new CheckSubjectRender("拒绝"));
        table.getColumnModel().getColumn(13).setCellEditor(new CheckSubjectEditor("拒绝"));

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void update(List<MatchBean> matchBeans) {
        SwingUtilities.invokeLater(() -> {
            this.matchBeans = matchBeans;
            adapter.setMatchBeans(matchBeans);
            table.updateUI();
        });
    }
}
