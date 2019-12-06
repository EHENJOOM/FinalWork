package com.zhk.panel.teacher.info.student;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.main.StudentBean;
import com.zhk.panel.teacher.subject.EditButtonEditor;
import com.zhk.panel.teacher.subject.EditButtonRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2019/11/27 16:16
 * @description 对学生信息操作的主面板
 */
public class OperateStudentPanel extends JPanel implements OperateStudentView, EventListener {

    private JTable table;

    private JComboBox<String> academyCombo;
    private JTextField nameTextField;
    private JTextField numberTextField;

    private OperateStudentAdapter adapter;

    private List<StudentBean> studentBeans;

    private OperateStudentPresenter presenter = new OperateStudentPresenter();

    public OperateStudentPanel() {
        initView();
        initPopupMenu();
        presenter.attachView(this);
        presenter.select();
        EventCenter.registerEventListener(this, Events.CHANGE_STUDENT);
    }

    private void initView() {
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        adapter = new OperateStudentAdapter();
        table = new JTable(adapter);
        scrollPane.setViewportView(table);

        String[] major = new String[]{"信息管理与信息系统", "会计", "物流", "财务管理", "国际贸易"};
        table.getColumnModel().getColumn(2).setCellEditor(new ComboBoxEditor(new String[]{"男", "女"}));
        table.getColumnModel().getColumn(3).setCellEditor(new ComboBoxEditor(major));
        table.getColumnModel().getColumn(4).setCellEditor(new ComboBoxEditor(Config.ACADEMY));
        table.getColumnModel().getColumn(6).setCellEditor(new EditButtonEditor());
        table.getColumnModel().getColumn(6).setCellRenderer(new EditButtonRender());

        JPanel headPanel = new JPanel(new GridLayout(1, 7, 10, 20));
        JLabel academyLabel = new JLabel("所属学院");
        JLabel nameLabel = new JLabel("姓名");
        JLabel numberLabel = new JLabel("学号");
        academyCombo = new JComboBox<>();
        nameTextField = new JTextField();
        numberTextField = new JTextField();
        JButton filterButton = new JButton("查找");

        filterButton.addActionListener(event -> filter());
        academyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        for (String item : Config.ACADEMY) {
            academyCombo.addItem(item);
        }

        headPanel.add(academyLabel);
        headPanel.add(academyCombo);
        headPanel.add(nameLabel);
        headPanel.add(nameTextField);
        headPanel.add(numberLabel);
        headPanel.add(numberTextField);
        headPanel.add(filterButton);

        add(scrollPane, BorderLayout.CENTER);
        add(headPanel, BorderLayout.NORTH);
    }

    /**
     * 初始化右键菜单
     */
    private void initPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("删除");
        JMenuItem addMenuItem = new JMenuItem("新建");
        popupMenu.add(deleteMenuItem);
        popupMenu.add(addMenuItem);

        deleteMenuItem.addActionListener(event -> {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "是否删除此学生？", "提示", JOptionPane.YES_NO_OPTION)) {
                int row = table.getSelectedRow();
                if (studentBeans.get(row).getId() == 0) {
                    studentBeans.remove(row);
                    table.updateUI();;
                } else {
                    presenter.delete(studentBeans.get(row));
                }
            }
        });
        addMenuItem.addActionListener(event -> {
            StudentBean studentBean = new StudentBean();
            studentBean.setId(0);
            studentBean.setState(Config.UNCHANGED_INFO);
            studentBeans.add(studentBean);
            table.updateUI();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row == -1) {
                        return;
                    }

                    table.setRowSelectionInterval(row, row);
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
    }

    private void filter() {
        String academy =(String) academyCombo.getSelectedItem();
        String name = nameTextField.getText();
        String number = numberTextField.getText();

        List<StudentBean> list = studentBeans;
        if (academy != null && !academy.isEmpty()) {
            list = list.parallelStream()
                    .filter(studentBean -> studentBean.getAcademy().contains(academy) || studentBean.getAcademy().equals(academy))
                    .collect(Collectors.toList());
        }
        if (name != null && !name.isEmpty()) {
            list = list.parallelStream()
                    .filter(studentBean -> studentBean.getName().contains(name) || studentBean.getName().equals(name))
                    .collect(Collectors.toList());
        }
        if (number != null && !number.isEmpty()) {
            list = list.parallelStream()
                    .filter(studentBean -> studentBean.getNumber().contains(number) || studentBean.getNumber().equals(number))
                    .collect(Collectors.toList());
        }

        if (list.size() == 0) {
            showMessage("没有符合条件的记录！");
        } else {
            adapter.setStudentBeans(list);
            table.updateUI();
        }
    }

    @Override
    public void deleteApply(StudentBean data) {
        SwingUtilities.invokeLater(() -> {
            studentBeans.remove(data);
            table.updateUI();
        });
    }

    @Override
    public void update(List<StudentBean> studentBeans) {
        SwingUtilities.invokeLater(() -> {
            this.studentBeans = studentBeans;
            adapter.setStudentBeans(studentBeans);
            table.updateUI();
        });
    }

    @Override
    public void showMessage(String msg) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.CHANGE_STUDENT:
                // id为0代表是新建的对象，所以调用插入方法。id不为0则为已有对象，调用更新即可
                if (((StudentBean)object).getId() == 0) {
                    presenter.insert((StudentBean) object);
                } else {
                    presenter.update((StudentBean) object);
                }
                break;
            default:
                break;
        }
    }
}
